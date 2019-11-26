const mysql = require("mysql");
const db = undefined;

/**
 * Attempts to connect to the database with the given settings. Logs errors if connection cannot be established.
 * @param settings A settings object containing hostname, port, username, password, and database name.
 */
function connect(settings) {
	this.db = mysql.createConnection(settings);
	
	this.db.connect(function(err)
	{
		if (!err) {
			console.log("Database at " + settings.host + " connected on port " + settings.port);
		} else {
			console.log("Error connecting to database");
		}
	});
	
}

/**
 * Returns the instantiated database object.
 */
function getDB () {
	return this.db;
}

/**
 * Performs a given query asynchronously.
 * @param queryStr The raw SQL query to use.
 * @returns A promise containing `err` and `rows`, where err is the error (if any) and rows is the query result
 */
async function queryAsync(queryStr){
	let result = {err:null, rows:null};
    const query = new Promise((resolve, reject) => {
        this.db.query(queryStr, (err, res) => {
            if(err){
            	result.err = err.message;
                return reject(`${err.message}`);
			} else {
				result.rows = res;
                resolve();
			}
        });
    }).catch( reason => {
    	result.err =  reason;
	});
    await query;
  	return result;
}

module.exports = {
	db: db, 
	connect: connect,
    queryAsync: queryAsync
};
