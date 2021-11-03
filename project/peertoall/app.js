const express = require("express");
const http = require("http");
const app = express();
const port = 3000 || process.env.PORT;
const server = http.createServer(app);
const { Server } = require("socket.io");
const io = new Server(server);
const { v4: uuidV4 } = require("uuid");

app.use(express.static("public"));
app.set("view engine", "ejs");

app.get("/", (req, res) => {
    res.redirect(`/${uuidV4()}`);
});

app.get("/:room", (req, res) => {
    res.render("room", { roomID: req.params.room });
});

io.on("connection", socket => {
    console.log("User Connected");
    socket.on("share-ID", ({roomID, peerID, userName}) => {
        socket.join(roomID);
        socket.broadcast.to(roomID).emit("new-user-joined", {peerID, userName});
        socket.on("disconnect", () => {
            console.log("User Disconnect");
            socket.broadcast.to(roomID).emit("user-disconnected", peerID);
        });
        socket.on("remote-close-stream", peerID => {
            socket.broadcast.to(roomID).emit("peer-close-stream", peerID);
        });
    });
});

server.listen(port, () => {
    console.log(`listening in port: ${port}`);
});