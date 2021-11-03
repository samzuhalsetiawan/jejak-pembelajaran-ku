const https = require("https");
const fs = require("fs");
const express = require("express");
const path = require("path");
const port = 443;

const app = express();

app.use(express.static("public"));

const httpsConfig = {
    cert: fs.readFileSync("samzuhal.dev+4.pem"),
    key: fs.readFileSync("samzuhal.dev+4-key.pem")
}

const server = https.createServer(httpsConfig, app);

app.get("/", (req, res) => {
    res.sendFile(path.join(__dirname, "zoom.html"));
});

server.listen(port, () => {
    console.log(`listening in port: ${port}`);
});