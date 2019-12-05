const express = require("express");
const router = express.Router();
const spendingModel = require("../model/spending");
const userAuth = require("../authentication/userAuth");

/**
 * Implements the /spending/months endpoint which gets the valid months for which the current user has
 * spending data.
 */
router.get("/spending/months", async function(req, res) {
    let email = userAuth.getUserEmailByToken(req.body.authToken);
    let result = await  spendingModel.getValidMonths(email);

    res.writeHead(200, {"Content-Type": "text/plain"});
    if(result.success){
        res.end(JSON.stringify(result.data), "utf-8");
    } else {
        res.end(JSON.stringify(result));
    }
});

/**
 * Implements the /spending/bycategory endpoint which returns a list of expenses for the given user broken down by
 * secondary category, as well as totals and a count of transactions in that category. This endpoint is
 * parameterized with a 'month' header which can filter the returned data to a given month. If the month header is
 * absent, all-time data is returned.
 */
router.get("/spending/bycategory", async function(req, res) {
    let email = userAuth.getUserEmailByToken(req.body.authToken);

    let result = null;
    if(req.headers.month){
        result = await  spendingModel.spendingByCategoryForUserForMonth(email, req.headers.month);
    } else {
        result = await  spendingModel.spendingByCategoryForUser(email);
    }

    res.writeHead(200, {"Content-Type": "text/plain"});
    if(result.success){
        res.end(JSON.stringify(result.data), "utf-8");
    } else {
        res.end(JSON.stringify(result));
    }
});

/**
 * Implements the /income/bycategory endpoint which returns a list of income for the given user broken down by
 * secondary category, as well as totals and a count of transactions in that category. This endpoint is
 * parameterized with a 'month' header which can filter the returned data to a given month. If the month header is
 * absent, all-time data is returned.
 */
router.get("/income/bycategory", async function(req, res) {
    let email = userAuth.getUserEmailByToken(req.body.authToken);
    let result = null;
    if(req.headers.month){
        result = await  spendingModel.incomeByCategoryForUserForMonth(email, req.headers.month);
    } else {
        result = await  spendingModel.incomeByCategoryForUser(email);
    }
    res.writeHead(200, {"Content-Type": "text/plain"});
    if(result.success){
        res.end(JSON.stringify(result.data), "utf-8");
    } else {
        res.end(JSON.stringify(result));
    }
});

/**
 * Implements the /cashflow/all endpoint which returns a object containing income and expense totals for every
 * month for which the user has data.
 */
router.post("/cashflow/all", async function(req, res) {
    let email = userAuth.getUserEmailByToken(req.body.authToken);
    let response = await  spendingModel.cashFlowForUser(email);
    res.writeHead(200, {"Content-Type": "text/plain"});
    if(response.success){
        res.end(JSON.stringify(response.data), "utf-8");
    } else {
        res.end(JSON.stringify(response));
    }
});

module.exports = router;