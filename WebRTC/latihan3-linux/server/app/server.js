const express = require("express");
const app = express();
// const http = require("http");
const https = require("https");
const fs = require("fs");
const path = require("path");
// const server = http.createServer(app);
const options = {
    key: fs.readFileSync(path.resolve("./cert/key.pem")),
    cert: fs.readFileSync(path.resolve("./cert/cert.pem"))
}
const server = https.createServer(options, app);

const { v4: uuidV4 } = require("uuid");

const PORT = process.env.PORT || 3000;
const publicPath = path.resolve("../client/dist"); // Berdasarkan index.js

function runServer() {
    app.use(express.static(publicPath));

    app.get("/", (req, res) => {
        res.redirect("/" + uuidV4());
    });

    app.get("/:room", (req, res) => {
        res.sendFile(path.join(publicPath, "home.html"));
    });

    server.listen(PORT, () => {
        console.log(`Server is Listening in Port: ${PORT}`);
    });
}

module.exports = { runServer, server }