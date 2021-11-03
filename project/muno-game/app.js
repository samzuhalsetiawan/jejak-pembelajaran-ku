const express = require("express");
const app = express();
const http = require("http");
const server = http.createServer(app);
const { Server, Socket } = require("socket.io");
const { v4: uuidV4 } = require("uuid");
const io = new Server(server);
const PORT = process.env.PORT || 3000;

app.use(express.static("public"));
app.set('view engine', 'ejs');

const gameData = {}

app.get("/", (req, res) => {
    res.redirect(`/rooms/${uuidV4()}`);
});

app.get("/rooms/:room", (req, res) => {
    res.render('index');
});

io.on("connection", socket => {
    socket.on("player-connect", ({ roomID, playerID }) => {
        if (!gameData[roomID]) {
            gameData[roomID] = {}
        }
        if (gameData[roomID].hasOwnProperty(playerID)) {
            socket.emit("nama-player-sudah-ada", playerID);
        } else {
            socket.join(roomID);
            socket.broadcast.to(roomID).emit("player-joined", playerID);
            gameData[roomID][playerID] = socket;
        }
    });
});

server.listen(PORT, () => {
    console.log(`Server is Listening in Port: ${PORT}`);
});