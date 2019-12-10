const express = require("express");
const router = express.Router();
const userAuth = require("../authentication/userAuth.js");
const transactionModel = require('../model/transactions');

/**
 * Implements the /transactions/getall endpoint which returns all the transactions for the current user
 */
router.post("/transactions/getall", async function(req, res){
    let email = userAuth.getUserEmailByToken(req.body.authToken);
    const txs = await transactionModel.getCachedTransactionsFromEmail(email);
    if(txs){
        res.writeHead(200, {"Content-Type": "text/plain"});
        res.end(JSON.stringify({
            transactions: txs
        }), "utf-8");
    } else {
        res.writeHead(200, {"Content-Type": "text/plain"});
        res.end(JSON.stringify({success:false}), "getallutf-8");
    }
});

/**
 * Implements the /transactions/edit endpoint which allows a user to edit a given transaction's
 * description and primary/secondary categories. The transaction object must a body parameter.
 */
router.post('/transactions/edit', async function(req, res){
    let email = userAuth.getUserEmailByToken(req.body.authToken);
    console.log("Got edited transaction: " + JSON.stringify(req.body.transaction));
    const result = await transactionModel.editTransaction(req.body.transaction);
    if(result.success){
        res.writeHead(200, {"Content-Type": "text/plain"});
        res.end();
    } else {
        res.writeHead(200, {"Content-Type": "text/plain"});
        res.end(JSON.stringify({success:false}), "utf-8");
    }
});

/**
 * Implements the /transactions/delete endpoint which allows a user to delete a given transaction.
 * The transaction object must be a body parameter.
 */
router.post('/transactions/delete', async function(req, res){
    console.log("Got deleteing transaction: " + JSON.stringify(req.body.transaction));
    let email = userAuth.getUserEmailByToken(req.body.authToken);
    const result = await transactionModel.deleteTransaction(req.body.transaction);
    if(result.success){
        res.writeHead(200, {"Content-Type": "text/plain"});
        res.end();
    } else {
        res.writeHead(200, {"Content-Type": "text/plain"});
        res.end(JSON.stringify({success:false}), "utf-8");
    }
});

/**
 * Implements the /transactions/add endpoint which allows the user to add a new transaction to their Cash account
 * The transaction object must be a body parameter
 */
router.post('/transactions/add', async function(req, res){
    console.log("Add transaction: " + JSON.stringify(req.body.transaction));
    let email = userAuth.getUserEmailByToken(req.body.authToken);
    const result = await transactionModel.addTransaction(email, req.body.transaction);
    if(result.success){
        res.writeHead(200, {"Content-Type": "text/plain"});
        res.end();
    } else {
        res.writeHead(200, {"Content-Type": "text/plain"});
        res.end(JSON.stringify({success:false}), "utf-8");
    }
});

module.exports = router;