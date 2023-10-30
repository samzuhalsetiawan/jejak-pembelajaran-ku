const express = require("express");
const app = express();
const PORT = process.env.PORT || 3000;
const http = require("http");
const server = http.createServer(app);
const { Server } = require("socket.io");
const io = new Server(server)
const { v4: uuidV4 } = require("uuid")

app.use(express.static("public"))

app.get("/test/:status", (req, res) => {
    console.log("connected")
    const status = req.params.status
    if (status == "success") {
        const payload = {
            data: "Ini datanya"
        }
        res.send(payload)
    } else {
        const payload = {
            error: {
                message: "error message"
            }
        }
        res.send(payload)
    }
})

io.on("connection", socket => {
    console.log(`socket connected: ${socket.id}`)
    socket.on("new_message", payload => {
        const chatId = payload.chatId
        const targetId = payload.targetId
        const message = payload.message
        console.log(`new chat: ${chatId}`)
        // io.sockets[targetId].emit("new_message", { chatId, message })
        socket.broadcast.emit("new_message", { chatId, message })
        io.emit("new_message", { chatId, message })
    })
});

server.listen(PORT, () => { 
    console.log(`listening on ${PORT}...`) 
})