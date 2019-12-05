const express = require("express");
const router = express.Router();
const accountsModel = require("../model/accounts");
const userAuth = require("../authentication/userAuth");

/**
 * Implements the /accounts/getall endpoint which returns a list of all account details owned by the user
 */
router.get("/accounts/getall", async function(req, res) {
    let email = userAuth.getUserEmailByToken(req.body.authToken);
    let response = await  accountsModel.getAllUserAccounts(email);

    res.writeHead(200, {"Content-Type": "text/plain"});
    if(response.success){
        res.end(JSON.stringify(response.accounts), "utf-8");
    } else {
        res.end(JSON.stringify(response));
    }
});

/**
 * Implements the /accounts/balancehistory endpoint which returns a list balance history objects for each account
 * owned by the user.
 */
router.get("/accounts/balancehistory", async function(req, res) {
    let email = userAuth.getUserEmailByToken(req.body.authToken);
    let response = await  accountsModel.getBalancesByEmail(email);

    res.writeHead(200, {"Content-Type": "text/plain"});
    if(response.success){
        res.end(JSON.stringify(response.balances), "utf-8");
    } else {
        res.end(JSON.stringify(response));
    }
});


module.exports = router;