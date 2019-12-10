function webServer(settings) {
	const cors = require('cors');
    const bodyParser = require("body-parser");
    const fs = require("fs");
    const express = require("express");
    const https = require("https");
    const http = require("http");
	
	// Bring in routes
    const userRoutes = require("../routes/user.js");
    const linkedAccountRoutes = require("../routes/linkedAccount");
    const authMiddleware = require("../routes/authMiddleware.js");
    const testingRoutes = require('../routes/testingEnpoint');
    const transactionRoutes = require('../routes/transactions');
    const accounttsRoutes = require('../routes/accounts');
    const spendingRoutes = require('../routes/spending');
    const categoryROutes = require('../routes/categories')

    const app = express();

    // Use CORS so we can serve requests through a separate domain (e.g the web-page)
    app.use(cors());
	app.use(bodyParser.urlencoded({
		extended: true
	}));
	app.use(bodyParser.json());
	app.use(express.static(settings.resources)); //tell express where to look for static resources
	
	// Request for the home page
	app.get("/", function(req, res) {
		res.sendFile(settings.indexPage);
	});
	
	
	// Tell Express to use these routes
	app.use(authMiddleware);
	app.use(userRoutes);
	app.use(linkedAccountRoutes);
	app.use(testingRoutes);
	app.use(transactionRoutes);
	app.use(accounttsRoutes);
	app.use(spendingRoutes);
	app.use(categoryROutes);

	// Start the server
    const httpServer = http.createServer(app).listen(settings.httpPort);
	console.log("HTTP Server listening on port " + settings.httpPort);
}

module.exports = webServer;
