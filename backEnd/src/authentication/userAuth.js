const validator = require("../validator/token.js");
const tokenKeystore = new validator();

/**
 * Returns the email associated with the given token
 * @param token The session token
 * @returns The email address associated with the given token, or  null
 */
function getUserEmailByToken(token){
	return tokenKeystore.getUser(token);
}

/**
 * Creates a new session for the given email address
 * @param email The email address for which to create a new session
 * @returns The token representing the new session
 */
function addUser(email) {
	return tokenKeystore.addToken(email);
}

/**
 * Verifies if the given token is for a valid session
 * @param token The session token
 * @returns True if given token is for a valid session
 */
function checkSession(token) {
	return tokenKeystore.verifyToken(token);
}

/**
 * Ends the sesion for the given token
 * @param token Session token to expire
 */
function endSession(token) {
	tokenKeystore.removeToken(token);
}


module.exports = {
	addUser: addUser,
	checkSession: checkSession,
	endSession: endSession,
    getUserEmailByToken: getUserEmailByToken
};
