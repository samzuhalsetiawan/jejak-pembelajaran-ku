const express = require("express");
const app = express();
const path = require("path");
const http = require("http");
const server = http.createServer(app);
const { v4: uuidV4 } = require("uuid");
const PORT = process.env.PORT || 3000;
const { config } = require("../config");
const net = require('net');
const client = net.connect({ port: 80, host: "google.com" }, () => {
  config.mediasoup.webRtcTransport.listenIps[0].announcedIp = client.localAddress;
});

const publicPath = path.resolve("./public");

async function runServer() {
  app.use(express.static(publicPath));

  app.get("/", (req, res) => {
    res.redirect("/" + uuidV4());
  });

  app.get("/:room", (req, res) => {
    res.sendFile(path.join(publicPath, "home.html"));
  });

  server.listen(PORT, () => console.log(`Server is Listening in port: ${PORT}`));
}

module.exports = { runServer, server }