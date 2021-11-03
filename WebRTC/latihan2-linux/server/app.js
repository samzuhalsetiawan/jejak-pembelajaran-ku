const express = require("express");
const app = express();
const http = require("http");
const server = http.createServer(app);
const { Server } = require("socket.io");
const io = new Server(server);
const path = require("path");
const { v4: uuidV4 } = require("uuid");
const PORT = process.env.PORT || 3000;
const { createWorker, getWorker } = require("./worker");
const { websocket } = require("./socket");

const publicPath = path.resolve("../client/dist");

app.use(express.static(publicPath));

createWorker();

app.get("/", (req, res) => {
    res.redirect("/" + uuidV4());
});

app.get("/:room", (req, res) => {
    res.sendFile(path.join(publicPath, "home.html"));
});

websocket(io);

server.listen(PORT, () => {
    console.log(`Server is Listening in Port: ${PORT}`);
});