const WebSocket = require("ws");
const express = require("express");
const app = express();
const port = process.env.PORT || 3000;
// const http = require("http");
// const serv = http.createServer(app);
// const server = new WebSocket(serv);
const server = new WebSocket.Server({port:8080});

let chat = [];

app.use(express.static("public"));

app.get("/", (req, res) => {
    res.sendFile(__dirname + "/public/index.html");
});

app.listen(port, () => {
    server.on("connection", socket => {
        socket.on("message", message => {
            chat.push(message.toString());
            socket.send(JSON.stringify(chat));
        });
        socket.send(JSON.stringify(chat));
    });
});
