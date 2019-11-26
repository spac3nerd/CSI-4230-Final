const transactionModel = require('./transactions');
const TEST_ACCESS_TOKEN = "access-sandbox-f391e89e-2613-4c5d-970f-4679c45733d4";
const TEST_ITEM_ID = "zP6QlzGJ6jSEXJ4nDgBgsyjlbM5DRriRLoGyGl";
const TEST_REQUEST_ID = "T0lEN";

/**
 * A sample of what the PLAID response looks like for exchanging a public_key for an access_token
 * @type {{access_token: string, item_id: string, request_id: string, status_code: number}}
 */
const TESTTOKENRESPONSE =
    {
        "access_token":"access-sandbox-7c39360f-91c8-460b-aa15-eaa920e0e1f9",
        "item_id":"rr8oNnZkk1s7lB9dgn67HerrgRoLxKf86m9lm",
        "request_id":"UUzGE","status_code":200
    };

/**
 * A sample of what the PLAID response looks from getItem with a valid access_token
 * @type {{item: {available_products: string[], billed_products: string[], error: null, institution_id: string, item_id: string, webhook: string}, request_id: string, status_code: number}}
 */
const ITEMRESPONSE =
    {
        "item": {
            "available_products":["auth","balance"],
            "billed_products": ["transactions"],
            "error":null,
            "institution_id":"ins_1",
            "item_id":"rr8oNnZkk1s7lB9dgn67HerrgRoLxKf86m9lm",
            "webhook":""
        },
        "request_id":"ROFWw",
        "status_code":200
    };

/**
 * A sample of what the PLAID response looks like from getAuth with a valid access_token
 * @type {{accounts: *[], item: {available_products: string[], billed_products: string[], error: null, institution_id: string, item_id: string, webhook: string}, numbers: *[], request_id: string, status_code: number}}
 */
const AUTHRESPONSE =
    {
        "accounts": [{
            "account_id":"PKnANDwvv6unNxEMRGmnsd1NL7MeX3HoKePjV",
            "balances":{"available":100,"current":110,"limit":null},
            "mask":"0000","name":"Plaid Checking",
            "official_name":"Plaid Gold Standard 0% Interest Checking",
            "subtype":"checking",
            "type":"depository"
        }],
        "item":{
            "available_products":["balance"],
            "billed_products":["auth","transactions"],
            "error":null,
            "institution_id":"ins_1",
            "item_id":"rr8oNnZkk1s7lB9dgn67HerrgRoLxKf86m9lm",
            "webhook":""
        },
        "numbers":[{
            "account":"1111222233330000",
            "account_id":"PKnANDwvv6unNxEMRGmnsd1NL7MeX3HoKePjV",
            "routing":"011401533",
            "wire_routing":"021000021"
        }],
        "request_id":"WS5T8",
        "status_code":200
    };

/**
 * A sample of what the PLAID response looks like from getTransactions with a valid access_token
 * @type {{accounts: *[], item: {available_products: string[], billed_products: string[], error: null, institution_id: string, item_id: string, webhook: string}, request_id: string, total_transactions: number, transactions: *[], status_code: number}}
 */
const TRANSACTIONRESPONSE =
    {
        "accounts":[{
            "account_id":"PKnANDwvv6unNxEMRGmnsd1NL7MeX3HoKePjV",
            "balances": {"available":100,"current":110,"limit":null},
            "mask":"0000",
            "name":"Plaid Checking",
            "official_name":"Plaid Gold Standard 0% Interest Checking",
            "subtype":"checking",
            "type":"depository"
        }],
        "item":{
            "available_products":["balance"],
            "billed_products":["auth","transactions"],
            "error":null,
            "institution_id":"ins_1",
            "item_id":"rr8oNnZkk1s7lB9dgn67HerrgRoLxKf86m9lm",
            "webhook":""
        },
        "request_id":"5csyJ",
        "total_transactions":16,
        "transactions":[{
            "account_id":"pqomkneNNZuRQZJ35jmRFZjKgd1GnKCBNdaDDb",
            "account_owner":null,
            "amount":78.5,
            "category":null,
            "category_id":null,
            "date":"2018-03-25",
            "location":{
                "address":null,
                "city":null,
                "lat":null,
                "lon":null,
                "state":null,
                "store_number":null,
                "zip":null
            },
            "name":"Touchstone Climbing",
            "payment_meta":{
                "by_order_of":null,
                "payee":null,
                "payer":null,
                "payment_method":null,
                "payment_processor":null,
                "ppd_id":null,
                "reason":null,
                "reference_number":null
            },
            "pending":false,
            "pending_transaction_id":null,
            "transaction_id":"GWL6KDmvvAHeov6QAqpeha9kKR7eDKUVQdL9D",
            "transaction_type":"unresolved"
        },
        {
            "account_id":"PKnANDwvv6unNxEMRGmnsd1NL7MeX3HoKePjV",
            "account_owner":null,
            "amount":-500,
            "category":["Travel","Airlines and Aviation Services"],
            "category_id":"22001000",
            "date":"2018-03-25",
            "location":{"address":null,"city":null,"lat":null,"lon":null,"state":null,"store_number":null,"zip":null},
            "name":"United Airlines",
            "payment_meta":{
                "by_order_of":null,
                "payee":null,
                "payer":null,
                "payment_method":null,
                "payment_processor":null,
                "ppd_id":null,
                "reason":null,
                "reference_number":null
            },
            "pending":false,
            "pending_transaction_id":null,
            "transaction_id":"jGQ6rnaBBNIbVzMDW8nbfE68nqpdq5uPA69ABW",
            "transaction_type":"special"
        }],
        "status_code":200
    };

/**
 * Returns (asynchronously) the list of sample PLAID transactions
 * @returns {Promise<*[]|Array<"plaid".Transaction>|*>}
 */
async function getTestTransactions(){
    let response = await transactionModel.getTransactionsAndBalancesFromAccessKey(TEST_ACCESS_TOKEN);
    return response.transactions;
}

/**
 * Returns (asynchronously) the list of sample PLAID accounts
 * @returns {Promise<*[]|Array<"plaid".Transaction>|*>}
 */
async function getTestAccounts(){
    let response = await transactionModel.getTransactionsAndBalancesFromAccessKey(TEST_ACCESS_TOKEN);
    return response.accounts;
}

module.exports = {
    getTestTransactions,
    getTestAccounts
};