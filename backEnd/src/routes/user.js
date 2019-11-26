const express = require("express");
const router = express.Router();
const userModel = require("../model/user.js");
const userAuth = require("../authentication/userAuth.js");
const transactionsModel = require('../model/transactions');
const accountModel = require('../model/accounts');

/**
 * Implements the /user/login endpoint which attempts to log the user in, and returns an session token upon
 * success
 */
router.post("/user/login", function(req, res) {
	function dbCallback(result) {
		if (result.success) {
			var newToken = userAuth.addUser(req.body.email);
			console.log(newToken);
			res.writeHead(200, {
				"Content-Type": "text/plain"
			});
            userModel.getUserIDfromEmail(req.body.email,(result) => {
            	if(result.success){
                    transactionsModel.updateTransactionsAndBalances(result.userId)
				} else {
            		console.warn("Couldn't find userId for: " + req.body.email)
				}
			});
			res.end(JSON.stringify({success: result.success, authtoken: newToken}), "utf-8");
		} else {
            res.writeHead(200, {
                "Content-Type": "text/plain"
            });
            res.end(JSON.stringify(result), "utf-8");
		}
	};
	userModel.login(req.body, dbCallback);
});

/**
 * Implements the /user/verifyToken endpoint which can be used to check if a session token is still valid without
 * necessarily logging in or out. Returns an object with the success status of the verification.
 */
router.post("/user/verifyToken", function(req, res) {
	if (req.headers.authtoken) {
		if (userAuth.checkSession(req.headers.authtoken)) {
            let result = {
				success: true
			};
			res.writeHead(200, {
				"Content-Type": "text/plain"
			});
			res.end(JSON.stringify(result), "utf-8");
		}
		else {
            let result = {
				success: false
			};
			res.writeHead(200, {
				"Content-Type": "text/plain"
			});
			res.end(JSON.stringify(result), "utf-8");
		}
	}
	else {
		let result = {
			success: false
		};
		res.writeHead(200, {
			"Content-Type": "text/plain"
		});
		res.end(JSON.stringify(result), "utf-8");
	}
});

/**
 * Implements the /user/register endpoint which attempts to register the user with the given credentials. Does
 * not automatically log the user in. Returns an objecting containing the success status of registerring.
 */
router.post("/user/register", function(req, res) {
	function dbCallback(result) {
		if (result.success) {
			accountModel.createCashAccount(req.body.email);
			res.writeHead(200, {"Content-Type": "text/plain"});
			res.end(JSON.stringify(result), "utf-8");
		}
		else {
			res.writeHead(200, {"Content-Type": "text/plain"});
			res.end(JSON.stringify(result), "utf-8");
		}
	}
	userModel.register(req.body, dbCallback);
});

module.exports = router;
