var webServer = require("./src/server/server.js");
var dbInstance = require("./src/model/dbConnect.js");
var server;

var serverOptions = {
	baseURL: global.baseURL,
	httpPort: 8080,
	resources: __dirname + "/public",
	indexPage: __dirname + "/public/index.html"
};

global.baseURL = "http://localhost" + ":" + serverOptions.httpPort;

var dbOptions = {
	host: "localhost",
	user: "root",
	password: "",
	database: "csi5510",
	port: 3306
};

/**
 * Initializes the database connection and webserver object. Also prints the database timeout for convenience.
 */
function init() {
	dbInstance.connect(dbOptions);
	server = new webServer(serverOptions, dbOptions);
    dbInstance.db.query("show VARIABLES like 'connect_timeout'", function(err, rows){
        let timeout = rows[0].Value;
        console.log("Timeout: " + timeout);
    });
}


init();
