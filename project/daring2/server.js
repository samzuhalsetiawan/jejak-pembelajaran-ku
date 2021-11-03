const express = require("express");
const app = express();
const http = require("http").createServer(app);
const io = require("socket.io")(http);
const port = process.env.PORT || 3000;

app.use(express.static("public"));
app.set("view engine", "ejs");

let userTerkoneksi = [];

app.get("/", (req, res) => {
    res.render("index");
})

io.on("connection", socket => {
    userTerkoneksi.push(socket.id);
    socket.broadcast.emit("update-user-list", { listUserID : userTerkoneksi});

    socket.on('mediaOffer', data => {
        socket.to(data.to).emit('mediaOffer', {
            from: data.from,
            offer: data.offer
        });
    });
      
    socket.on('mediaAnswer', data => {
        socket.to(data.to).emit('mediaAnswer', {
            from: data.from,
            answer: data.answer
        });
    });
      
    socket.on('iceCandidate', data => {
        socket.to(data.to).emit('remotePeerIceCandidate', {
            candidate: data.candidate
        });
    });

    socket.on("disconnect", () => {
        userTerkoneksi = userTerkoneksi.filter(user => user !== socket.id);
        socket.broadcast.emit("update-user-list", { listUserID : userTerkoneksi});
    });
});

http.listen(port, () => {
    console.log("Server listen");
});

