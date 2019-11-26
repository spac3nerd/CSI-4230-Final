const express = require("express");
const router = express.Router();
const testingModel = require('../model/testing');

/**
 * Implements the /testing/accounts endpoint which returns a hard-coded list of account details for
 * quickly testing interface designs.
 */
router.get("/testing/accounts", async function(req, res) {
    res.writeHead(200, {
        "Content-Type": "text/plain"
    });
    let status = await testingModel.getTestAccounts();
    res.end(JSON.stringify(status), "utf-8");
});

/**
 * Implements the /testing/transactions endpoint which returns a hard-coded list of transactions for quickly
 * testing interface designs.
 */
router.get("/testing/transactions", async function(req, res){
    res.writeHead(200, {
        "Content-Type": "text/plain"
    });
    let status = await testingModel.getTestTransactions();
    res.end(JSON.stringify(status), "utf-8");
});

module.exports = router;
