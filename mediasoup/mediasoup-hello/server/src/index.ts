import express, { Request, Response } from "express";
import http from "http";
import { WebSocketServer } from "ws";

const app = express();
const server = http.createServer(app);
const webSockerServer = new WebSocketServer({ server, path: "/ws" });

app.get("/", (req: Request, res: Response) => {
  res.send("Hello, Express!");
});

webSockerServer.on("connection", socket => {
  console.log("New client connected");

  socket.on("message", (message) => {
    console.log(`Received message: ${message}`);
    // Echo the message back to the client
    socket.send(`Server received: ${message}`);
  });

  socket.on("close", () => {
    console.log("Client disconnected");
  });
}); 

server.listen(3000, () => {
   console.log("Server is running on http://localhost:3000");
});