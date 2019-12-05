const userModel = require('./user');
const dbInstance = require("./dbConnect");
const db = dbInstance.db;

/**
 * Creates the initial entries in account_details table for the account. Also creates the first entry in
 * account_balances for balance history.
 * @param userId The User ID of this account
 * @param accountId The unique account ID from PLAID
 * @param name The account name
 * @param officialName The long account name
 * @param type The acccount type (credit,debit, special)
 * @param subType The account sub-type
 * @param available The available balance
 * @param current The current balance
 * @param limit The credit limit (for credit accounts, else null)
 * @param callback Function to execute when finished.
 */
function addUserAccount(userId, accountId, name, officialName, type, subType, available, current, limit, callback){
    let queryStr = `INSERT into account_details (account_id, user_id, name, official_name, type, subtype) VALUES \
                    ("${accountId}", ${userId}, "${name}", "${officialName}", "${type}", "${subType}")`;
    db.query(queryStr, function(err,rows) {
      if(!err){
          // Add first-time account balances for new account
          updateAccountBalance(accountId, available, current, limit, function(result){
              callback(result)
          })
      } else {
          callback({success:false, message:("Could not add account: " + name)})
      }
    });
}

/**
 * Creates a new row in account_balances for the given account. Used for balance history
 * @param accountId The unique ID of the account
 * @param available The available balance
 * @param current The current balance
 * @param limit The credit limit
 * @param callback Function to execute when finished
 */
function updateAccountBalance(accountId, available, current, limit, callback){
    const QUERY= `INSERT into account_balances (account_id, date, available, current, credit_limit) VALUES \
    ("${accountId}", curdate(), ${available}, ${current}, ${limit})`;
    db.query(QUERY, function(err, rows) {
        if (!err) {
            callback({success: true})
        } else {
            callback({success: false, message: err})
        }
    });
}

/**
 * Creates a new row in account_balances for the given account, async. Used for balance history.
 * @param accountId The unique ID of the account
 * @param available The available balance
 * @param current The current balance
 * @param limit The credit limit
 * @returns A promise containing the success status of the operation
 */
async function updateAccountBalanceAsync(accountId, available, current, limit){
    const QUERY= `INSERT into account_balances (account_id, date, available, current, credit_limit) VALUES \
    ("${accountId}", curdate(), ${available}, ${current}, ${limit})`;
    let balancesResponse = await dbInstance.queryAsync(QUERY);
    if(!balancesResponse.err){
        return {success:true};
    } else {
        return {success:false, message: balancesResponse.err};
    }
}

/**
 * Wrapper function for adding multiple accounts at once.
 * @param accounts A list of account objects to add. Must contain all parameters for addUserAccount()
 * @param userId The User ID associated with these accounts
 * @param callback Function to execute when finished
 */
function addMultipleAccountsAsync(accounts, userId, callback){
    function asyncAction(userId, account) {
        return new Promise(function(resolve, reject) {
            addUserAccount(userId, account.account_id, account.name, account.official_name, account.type, account.subtype,
                account.balances.available, account.balances.current, account.balances.limit,
                function(result) {
                    if (!result.success) {
                        return reject(result);
                    } else{
                        return resolve(result);
                    }

                });
        });
    }
    let promises = [];
    for (let i=0; i < accounts.length; i++) {
        promises.push(asyncAction(userId, accounts[i]));
    }
    Promise.all(promises).then(function AcceptHandler(results) {
        callback(results[0])
    }, function ErrorHandler(error) {
        callback(error);
    });
}

/**
 * Returns all the account_details rows for the given user ID, async.
 * @param email The email address of the user to retrieve accounts for.
 * @returns A promise with the success status of the operation, and a payload of the account_details rows.
 */
async function getAllUserAccounts(email) {
    let response = await userModel.getUserIDfromEmailAsync(email);
    if(response.success){
        const QUERY = `SELECT * from account_details where user_id = ${response.userId}`;
        let accountsResponse = await dbInstance.queryAsync(QUERY);
        if(!accountsResponse.err){
            return {success: true, accounts: accountsResponse.rows};
        }
        else {
            return {success: false, message: accountsResponse.err};
        }
    }
    else {
        return response;
    }
}

/**
 * Retrieves balance history for all the accounts associated with the given user, async.
 * @param email The email address of the user to retrieve accounts for.
 * @returns A promise with the success status of the opreation, and a payload of account history snapshots for all
 * accounts owned by the user.
 */
async function getBalancesByEmail(email){
    const response = await userModel.getUserIDfromEmailAsync(email);
    if (response.success){
        const QUERY = `SELECT account_details.account_id, account_details.name, account_balances.date, account_details.type,\
        account_balances.available, account_balances.current, account_balances.credit_limit FROM account_balances \
        INNER JOIN account_details on account_details.account_id=account_balances.account_id \ 
        where account_details.user_id = ${response.userId} order by date`;
        let balancesResponse = await dbInstance.queryAsync(QUERY);
        if(!balancesResponse.err){

            let balanceData = [];
            balancesResponse.rows.forEach(function (row) {
                let account = null;
                for(let i in balanceData){
                    if(balanceData[i].account_id === row.account_id){
                        account = balanceData[i];
                        break;
                    }
                }
                if(account === null){
                    account = {account_id:row.account_id, account_name:row.name, history:{} };
                    balanceData.push(account);
                }
                account.history[row.date] = row.current;
            });

            //we must change the history object to an array
            for (let k = 0; k < balanceData.length; k++) {
                let newHistoryData = [];
                for (let n in balanceData[k].history) {
                    newHistoryData.push({
                        date: n,
                        value: balanceData[k].history[n]
                    });
                }
                balanceData[k].history = newHistoryData;
            }

            return {success: true, balances: balanceData};
        }
        else {
            return {success: false, message: balancesResponse.err};
        }
    }
    else {
        return response;
    }
}

/**
 * Creates the initial 'cash' account for the given user, async.
 * @param email The email address of the user to create the cash account
 * @returns A promise containing the success of the operation.
 */
async function createCashAccount(email){
    let response = await userModel.getUserIDfromEmailAsync(email);
    if(response.success) {
        let acct_id = `cash-account-user-${response.userId}`;
        const QUERY=`INSERT INTO account_details(account_id, user_id, name, official_name, type, subtype)\
         VALUES ("${acct_id}", ${response.userId}, "Cash", "Cash", "despository", "cash")`;
        let result = await dbInstance.queryAsync(QUERY);
        if(!result.err){
            console.log("Succesfully created cash account: " + acct_id);
            return {success: true};
        } else {
            console.log("Failed to add cash account: " + acct_id + " Error: " + result.err);
            return {success: false, message: result.err};
        }
    } else {
        return response;
    }
}
module.exports = {
    createCashAccount,
    addUserAccount,
    addMultipleAccountsAsync,
    getAllUserAccounts,
    updateAccountBalance,
    updateAccountBalanceAsync,
    getBalancesByEmail
}