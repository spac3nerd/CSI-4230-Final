const express = require("express");
const router = express.Router();
const linkedAccountsModel = require("../model/linkedAccounts.js");
const userAuth = require("../authentication/userAuth.js");

/**
 * Implements the /linkedAccounts/add endpoint which handles the initial setup of accounts and transactions
 * for the given Linked-Account from a PLAID Link session
 */
router.post("/linkedAccounts/add", function(req, res) {
    const email = userAuth.getUserEmailByToken(req.headers.authtoken);
    const new_key = req.body.new_key;
    linkedAccountsModel.addAccountAndUpdate(email,new_key, (result) => {
        res.writeHead(200, {"Content-Type": "text/plain"});
        res.end(JSON.stringify(result), "utf-8");
    });
});

module.exports = router;