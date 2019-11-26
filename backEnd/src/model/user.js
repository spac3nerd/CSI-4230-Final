const bcrypt = require("bcrypt");
const salt = "$2a$10$2pue8UkToY/dqotJLO3ivO";
const dbInstance = require("./dbConnect");
const db = dbInstance.db;

/**
 * Attempts to login the user with the given credentials.
 * @param credentials An object containing the email and password sent by user
 * @param callback A function to execute when finished. The parameter contains the success status of the operation.
 */
function login(credentials, callback) {
    const email = credentials.email;
    const password = credentials.password;

    db.query("SELECT * FROM user_table WHERE user_email = '" + email + "'", function (err, rows) {
        if (!err) {
            if (rows.length >= 1) {
                if (bcrypt.hashSync(password, salt) === rows[0].password) {
                    callback({success: true});
                }
                else {
                    callback({
                        success: false,
                        message: "Invalid password"
                    });
                }
            }
            else {
                callback({
                    success: false,
                    message: "Invalid email"
                });
            }
        }
        else {
            throw err;
        }
    });
}

/**
 * Attempts to register the user with the given credentials.
 * @param credentials An object containing the email and password sent by user
 * @param callback A function to execute when finished. The parameter contains the success status of the operation.
 */
function register(credentials, callback) {
    const email = credentials.email;
    const password = credentials.password;
	db.query("SELECT * FROM user_table WHERE user_email = '" + email + "'", function(err, rows) {
		if (!err) {
			if (rows.length >= 1) {
				callback({
					success: false,
					message: "Email already exists!"
				});
			}
			else {
				let newHash = bcrypt.hashSync(password, salt);
				db.query("INSERT INTO user_table (user_email, password) VALUES ('" + email + "','" + newHash + "')", function (err, rows) {
					if (!err) {
						callback({success: true});
					} else {
						throw err;
					}
				});
			}
		}
		else {
			throw err;
		}
	});
}

/**
 * Helper function for getting the user ID of a given email address from the database.
 * @param email The email address to retrieve the user ID of.
 * @param callback Function to execute when complete. The parameter contains the success status of the operation, i
 * ncluding the user ID if successful
 */
function getUserIDfromEmail(email, callback){
    db.query("SELECT user_id from user_table where user_email = '" + email +"'", function (err, rows) {
        if ( !err ) {
            callback({success:true, userId: rows[0].user_id});
        }
        else{
            callback({success:false, message: err})
        }
    });
}

/**
 * Helper function for getting the user ID of a given email address from the database, asynchronously.
 * @param email The email address to retrieve the user ID of.
 * @returns {Promise<*>} A promise that contains the success status of the operation, including the user ID if successful
 */
async function getUserIDfromEmailAsync(email){
    let response = await dbInstance.queryAsync("SELECT user_id from user_table where user_email = '" + email +"'");
    if(!response.err){
        return {success:true, userId: response.rows[0].user_id};
    } else {
        return {success:false, message: response.err};
    }
}

module.exports = {
    login,
    register,
    getUserIDfromEmail,
    getUserIDfromEmailAsync
};
