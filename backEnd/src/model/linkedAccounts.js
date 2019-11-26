const plaid = require('plaid');
const dbInstance = require("./dbConnect");
const db = dbInstance.db;
const userModel = require('./user');
const accountsModel = require('./accounts');
const transactionModel = require('./transactions');

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
 * Exchanges the given public_key for an access_token from PLAID. Public keys expire in 30 minutes and can not
 * be used alone to gain access to user account data. Only an access_token provides read-access to user data.
 * @param public_key The PLAID public_key returns from the PLAID Link session.
 * @param callback Function to execute when finished.
 */
function exchangePublicKey(public_key, callback) {
    let access_token = undefined;
    plaid_client.exchangePublicToken(public_key, function(error, tokenResponse) {
        if (error != null) {
            var msg = 'Could not exchange public_token!';
            console.log(msg + '\n' + JSON.stringify(error));
            callback({
				success: false,
                error: (msg + '\n' + error),
            });
        } else {
            access_token = tokenResponse.access_token;
            ITEM_ID = tokenResponse.item_id;
            console.log('Access Token: ' + access_token);
            console.log('Item ID: ' + ITEM_ID);

            callback({
                success: true,
                access_token: access_token
            });
        }
    });
}


/***
 * Performs the logic to exchange a PLAID public_key for an access_token, and then stores the access_token in the
 * database for later refreshing and syncing. Finally, it gets all the accounts associated with the access_token and
 * does initialization in the database for those accounts.
 * @param user The email address of the user to add the account for.
 * @param public_key The public_key returned from the PLAID Link session
 * @param callback Function to execute when finished.
 */
function addAccount(user, public_key, callback) {
    userModel.getUserIDfromEmail(user, (result) => {
        if(result.success){
            var user_id = result.userId;
            exchangePublicKey(public_key, (result) => {
                if (result.success) {
                    db.query("INSERT INTO linked_accounts (user_id, public_key, access_token) VALUES ('" +
                        user_id + "','" + public_key + "','" + result.access_token + "')", function (err, rows) {
                        if ( !err ) {
                            var access_token = result.access_token;
                            getAuth(access_token, function(result) {
                                if(result.success){
                                    accountsModel.addMultipleAccountsAsync(result.accounts, user_id, function(result){
                                        if(result.success){
                                            callback(result)
                                        } else {
                                            callback(result)
                                        }
                                    });
                                } else {
                                    console.log("Something went wrong getting accounts: " + JSON.stringify(err));
                                    callback({success: false});
                                }
                            });
                        } else {
                            console.log("Something went wrong inserting into db: " + JSON.stringify(err));
                            callback({success: false});
                        }
                    });
                } else {
                    console.log("Something went wrong exchanging public key for access key");
                    callback({success: false});
                }
            });
        } else {
            console.log("Something went wrong getting ID from email: " + result.message);
            callback({success: false});
        }
    });
}

/**
 * Gets the list of accounts for the given access_token from PLAID.
 * @param ACCESS_TOKEN The access_token to query account info from.
 * @param callback Function to execute when complete.
 */
function getAuth(ACCESS_TOKEN, callback){
    plaid_client.getAuth(ACCESS_TOKEN, function(error, authResponse) {
        if (error != null) {
            var msg = 'Unable to pull accounts from the Plaid API.';
            callback({success: false, message: msg + '\n' + error});
        } else {
            callback({success: true, accounts: authResponse.accounts});
        }
    })
}

/**
 * Wrapper function that exchanges the given public_key for an access_token, initializes all the accounts for that
 * token, and does a first-time transaction and balances history initialization for those accounts.
 * @param email Email of the user for the given public_key
 * @param public_key The public_key returned from PLAID Link
 * @param callback Function to execute when finished
 */
function addAccountAndUpdate(email, public_key, callback){
    userModel.getUserIDfromEmail(email, function(result){
        if(result.success){
            let userId = result.userId;
            addAccount(email, public_key, function(result){
                if(result.success){
                    transactionModel.updateTransactionsAndBalances(userId); //Doesn't actually return anything for now
                    callback(result);
                } else {
                    console.log("Something went wrong linking new account. Not updating transactions: " + result.message);
                    callback(result);
                }
            })
        } else {
            callback(result);
        }
    })
}


module.exports = {
	addAccount,
    addAccountAndUpdate
};
