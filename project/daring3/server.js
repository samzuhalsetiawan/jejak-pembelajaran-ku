const express = require("express");
const { v4: uuidV4 } = require("uuid");
const app = express();
const { Server } = require("socket.io");
const http = require("http");
const server = http.createServer(app);
const io = new Server(server);

const port = process.env.PORT || 3000;

app.use(express.static("public"));
app.use(express.json());
app.use(express.urlencoded({extended : true}));
app.set("view engine", "ejs");

let clients = [];
let localDesc = {
    offer: null,
    answer: null
};

app.get("/", (req, res) => {
    const status = clients[0] ? "answering" : "offering";
    res.render("index", {status});
});

app.get("/offer", (req, res) => {
    res.json(JSON.stringify(localDesc.offer));
});

io.on("connection", socket => {
    clients.push(socket);
    socket.on("session-desc", desc => {
        if ((JSON.parse(desc.desc)).type == "offer") {
            localDesc.offer = JSON.parse(desc.desc);
            io.emit("new-offer",{});
        } else if ((JSON.parse(desc.desc)).type == "answer") {
            localDesc.answer = JSON.parse(desc.desc);
            io.emit("get-answer", {answer : localDesc.answer});
        }
    });
    socket.on("disconnect", reason => {
        clients.forEach(client => {
            if (client != socket) {
                socket.emit("user-disconnect", {});
            }
        });
        if (clients.indexOf(socket) > -1) {
            clients.splice(clients.indexOf(socket), 1);
        } else {
            console.log("socket not found");
        }
        console.log(reason);
    });
});

server.listen(port, () => {
    console.log("Server Listening");
});