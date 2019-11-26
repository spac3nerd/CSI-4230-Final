const express = require("express");
const router = express.Router();
const userAuth = require("../authentication/userAuth.js");

/**
 * Implements the Authentication Middleware router.
 * This will automatically send a failure message if requests that require authentication do not contain a session
 * token, or contain an invalid session token.
 *
 * If the request does not require authentication, e.g login, register, or testing, the middleware permits the
 * request to continue to be served by the next router.
 */
router.use(function(req, res, next) {
	if (req.headers.authtoken) {
		if (userAuth.checkSession(req.headers.authtoken)) {
			next();
		}
		else {
			let result = {
				success: false,
				message: "invalid token"
			};
			res.writeHead(403, {
				"Content-Type": "text/plain"
			});
			res.end(JSON.stringify(result), "utf-8");
			
		}
	}
	else {
 		if (req.path === "/user/login"  || req.path === "/user/register" || req.path.startsWith("/testing/")) {
			next();
        } else {
			res.writeHead(200, {
				"Content-Type": "text/plain"
			});
			res.end(JSON.stringify({
				success: false,
				message: "missing auth token"
			}), "utf-8");
		}
	}
});

module.exports = router;
