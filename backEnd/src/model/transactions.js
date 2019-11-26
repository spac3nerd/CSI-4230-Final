const plaid = require('plaid');
const dbInstance = require("./dbConnect");
const db = dbInstance.db;
const moment = require('moment');
const userModel = require('./user');
const accountsModel = require('./accounts');
const crypto = require("crypto");

const PLAID_CLIENT_ID = '5a7b28438d92390711093b24';
const PLAID_SECRET = 'ac167481f611cfbf848278c45f88ca';
const PLAID_PUBLIC_KEY = '1ce6a55cfc6e0c8cc294da8307f22d';
const PLAID_ENV = 'sandbox';

/**
 * Plaid Client instance. Used to access the PLAID API
 * @type {"plaid".Client}
 */
const plaid_client = new plaid.Client(
    PLAID_CLIENT_ID,
    PLAID_SECRET,
    PLAID_PUBLIC_KEY,
    plaid.environments[PLAID_ENV]
);

/**
 * Generates a unique ID for a transaction. Used when the user creates a new transaction for the Cash account which
 * PLAID doesn't know about.
 * @returns {string} 38-character random string representing a unique ID
 */
function uuid() {
    return crypto.randomBytes(19).toString("hex");
}

/**
 * Parses the category member from a PLAID transaction, which may be null, or an array, into an object
 * used by us with 'primary' and 'secondary' attributes. If the PLAID transaction has more than 2 categories, the
 * excess categories are dropped.
 * @param plaidCategories Either null, or an array of strings representing PLAID categories.
 * @returns {{primary: null, secondary: null}} An object used to set primary and secondary categories in the db.
 */
function parsePlaidCategories(plaidCategories){
    let result = {primary:null, secondary:null};
    if(plaidCategories){
        switch(plaidCategories.length){
            case 0:
                break;
            case 1:
                result.primary = plaidCategories[0];
                break;
            case 2:
                result.primary = plaidCategories[0];
                result.secondary = plaidCategories[1];
                break;
            default:
                result.primary = plaidCategories[0];
                result.secondary = plaidCategories[1];
                //console.log(`Not handling categories after ${categoriesStr[1]} in ${categoriesStr}`);
                break;
        }
    }
    return result;
}

/**
 * Returns (asynchronously) the transactions for the given access_key from PLAID.
 * @param access_key The access_key from which to get transactions
 * @returns {Promise<*>} A promise containing the success status of the operations and a list of transaction objects
 */
async function getTransactionsAndBalancesFromAccessKey(access_key){
    const ERR = {success:false, message: "Couldn't get new transactions and accounts from PLAID"};
    let startDate = moment().subtract(180, 'days').format('YYYY-MM-DD');
    let endDate = moment().format('YYYY-MM-DD');
    let response =  await plaid_client.getTransactions(access_key, startDate, endDate, {count: 500, offset: 0})
        .catch((reason) => {
            console.log("getNewTransactions failed:"  + JSON.stringify(reason));
            return undefined;
        });
    return response ? {success:true, transactions:response.transactions, accounts:response.accounts} : ERR;
}

/**
 * This function queries the database for all txs for a given user. Note, it does an inner join on account_details
 * to convert the account_id to an account name string. This is useful for sending to the client, but may not be what
 * you want if you need the account_id.
 * @param userId User ID in the DB to query
 * @returns {Promise<Object>} The DB result containing, {err, rows}.
 */
async function getCachedTransactions(userId){
    const QUERY = `SELECT transaction_id, transactions.date, account_details.account_id, account_details.name as account_name, \
        transactions.name, transactions.category_primary, transactions.category_secondary,transactions.amount FROM transactions INNER JOIN \
        account_details ON transactions.account_id=account_details.account_id where transactions.user_id = ${userId}`;

    return await dbInstance.queryAsync(QUERY);
}

/**
 * Wrapper that returns (asynchronously) all the stored transactions for the given email address
 * @param email The email address of the to get stored transactions for.
 * @returns {Promise<undefined>} A promise containing either the rows in the database for the transactions, or undefined
 * if there was an error.
 */
async function getCachedTransactionsFromEmail(email){
    let userResponse = await userModel.getUserIDfromEmailAsync(email);
    if(userResponse.success){
        let txsResponse = await getCachedTransactions(userResponse.userId); ``
        if(!txsResponse.err){
            return txsResponse.rows;
        } else {
            return undefined;
        }
    } else {
        console.log("no user found for: " + email + ": " + userResponse.message);
        return undefined;
    }
}

/**
 * Asynchronously updates all the transactions and balances for a given user using the following procedure:
 * 1. Get all previously stored transactions for this user from our database
 * 2. Get all access_tokens associated with this user
 * 3. Get all transactions and accounts for each access_token owned by this user
 * 4. For each transaction, determine if it's new, and add it to the database if so. Else, ignore it
 * 5. For each account, create a new account_balance entry for balance history purposes.
 * @param userId The user to perform the update for
 * @returns Nothing. Promise should be ignored.
 */
async function updateTransactionsAndBalances(userId){
    let cachedTxsResponse  = await getCachedTransactions(userId);
    if(!cachedTxsResponse.err){
        let cachedTxs = cachedTxsResponse.rows;
        let accounts_result = await dbInstance.queryAsync(`SELECT account_id, access_token FROM linked_accounts WHERE user_id = '${userId}'`);
        if(!accounts_result.err){
            for(let i in accounts_result.rows){
                let access_key = accounts_result.rows[i].access_token;
                let response = await getTransactionsAndBalancesFromAccessKey(access_key);
                if(response.success){
                    response.transactions.forEach( async function(tx) {
                        if(cachedTxs.find( (t) => {return t.transaction_id === tx.transaction_id})) {
                            console.log("Old transaction: " + tx.transaction_id + ". Not doing anything (Maybe should check for updated data)");
                        } else {
                            let parsedCategories = parsePlaidCategories(tx.category);
                            let queryStr = `INSERT into transactions (transaction_id, user_id, account_id, date, \
                        category_primary, category_secondary, amount, name) VALUES("${tx.transaction_id}", ${userId}, "${tx.account_id}", \
                        "${tx.date}", "${parsedCategories.primary}", "${parsedCategories.secondary}", ${tx.amount}, "${tx.name}")`;
                            let insertResult = await dbInstance.queryAsync(queryStr);
                            if(!insertResult.err){
                                console.log (`New tx: ${tx.transaction_id} inserted successfully`)
                            } else {
                                console.log(`New tx: ${tx.transaction_id} failed to insert: insertResult.err`)
                            }
                        }
                    });
                    response.accounts.forEach( function(acc) {
                       console.log("Should update for: " + acc.account_id + " balances: " + JSON.stringify(acc.balances));
                        accountsModel.updateAccountBalance(acc.account_id, acc.balances.available, acc.balances.current, acc.balances.limit,
                            function(result){
                                if(!result.success){
                                    console.log(`Error updating balance for acc: ${acc.account_id}. Msg: ${result.message}`)
                                }
                            });
                    });
                } else {
                    console.log("Error: " + response.message);
                }
            }
        } else {
            console.log("Error:" + accounts_result.err);
        }
    } else {
        console.log("Error:"  + cachedTxsResponse.err);
    }
}

/**
 * Updates the given transaction (by ID) in the database with new values for name, category_primary and category_secondary.
 * @param newTransaction The modified transaction object.
 * @returns {Promise<*>} A promise containing the success of the operation
 */
async function editTransaction(newTransaction){
    const QUERY = `UPDATE transactions SET name = "${newTransaction.name}", \
    category_primary = "${newTransaction.category_primary}", category_secondary = "${newTransaction.category_secondary}" \
    WHERE transaction_id = "${newTransaction.transaction_id}"`;
    const result = await dbInstance.queryAsync(QUERY);
    if(!result.err){
        return {success:true}
    } else {
        return {success:false, message:result.err}
    }
}

/**
 * Deletes a given transaction (by ID) from the database.
 * @param newTransaction The transaction to delete
 * @returns {Promise<*>} A promise containing the success of the operation
 */
async function deleteTransaction(newTransaction){
    const QUERY = `DELETE FROM transactions where transaction_id = "${newTransaction.transaction_id}"`;
    const result = await dbInstance.queryAsync(QUERY);
    if(!result.err){
        return {success:true}
    } else {
        return {success:false, message:result.err}
    }
}

/**
 * Inserts a new transaction into the database for the given user and transaction description. Generates a
 * new transaction ID for this transaction.
 * @param email The email of the user to add this transaction for.
 * @param tx The description of the transaction to add
 * @returns {Promise<*>} A promise containing the success of the operation
 */
async function addTransaction(email, tx){
    let userRes = await userModel.getUserIDfromEmailAsync(email);
    if(userRes.success){
        let tx_id = uuid();
        let tx_account = `cash-account-user-${userRes.userId}`;
        const QUERY = `INSERT INTO transactions(transaction_id, user_id, account_id, date, category_primary, \
        category_secondary, amount, name) VALUES ("${tx_id}",${userRes.userId}, "${tx_account}","${tx.date}",\
        "${tx.category_primary}","${tx.category_secondary}",${tx.amount},"${tx.name}")`;
        const result = await dbInstance.queryAsync(QUERY);
        if(!result.err){
            return {success:true}
        } else {
            console.log("Failed to add transaction: " + JSON.stringify(tx) + " Error: " + result.err);
            return {success:false, message:result.err}
        }
    } else {
        return userRes;
    }
}

module.exports = {
    addTransaction,
    editTransaction,
    deleteTransaction,
    updateTransactionsAndBalances,
    getTransactionsAndBalancesFromAccessKey,
    getCachedTransactionsFromEmail
};