tokenKeystore =  function() {
	var crypto = require("crypto");
	var tokens = {};

    /**
	 * Generate a unique string for use as an auth token
     * @returns {string}
     */
	function generateToken() {
		return crypto.randomBytes(64).toString("hex");
	}

	return {
        /**
		 * Creates a token and stores it for the given email address
         * @param email The email address to generate a token for
         * @returns {string} The auth token generated for the given user.
         */
		addToken: function(email) {
			var id = generateToken();
			tokens[id] = email;
			return id;
		},

        /**
		 * Looks up the email for the given token
         * @param id The token to look up the email of.
         * @returns The email address associated with the given token
         */
		getUser: function(id) {
			return tokens[id];
		},

        /**
		 * Removes the given token from the store
         * @param id The token to remove.
         */
		removeToken: function(id) {
			delete tokens[id];
		},

        /**
		 * Verifies whether the given token is for the given email. Or, if email is not provided, returns if the given
		 * token represents a current session.
         * @param token The token to check
         * @param email The email to check
         * @returns {boolean} Whether the token and email are associated with each other, or if the token is valid
		 * (when no email is provided)
         */
		verifyToken: function(token, email) {
			if (arguments.length === 1) {
				if (tokens[token]) {
					return true;
				}
				else {
					return false;
				}
			}
			else if (arguments.length === 2) {
				if (tokens[token] === email) {
					return true;
				}
				else {
					return false;
				}
			}
		}
	};
};

module.exports = tokenKeystore;
