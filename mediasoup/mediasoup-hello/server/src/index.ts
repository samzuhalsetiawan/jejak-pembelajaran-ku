import express, { type Request, type Response } from "express";
import http from "http";
import { WebSocketServer } from "ws";
import { MediasoupServer } from "./lib/MediasoupServer.js";

const app = express();
const server = http.createServer(app);
const webSocketServer = new WebSocketServer({ server, path: "/ws" });

app.get("/", (req: Request, res: Response) => {
  res.send("Hello, Express!");
});

await MediasoupServer.initialize(webSocketServer);

server.listen(3000, () => {
   console.log("Server is running on: http://localhost:3000");
});