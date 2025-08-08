import express, { Request, Response } from "express";
import http from "http";
import { WebSocketServer } from "ws";
import { WebSocketEvent, WebSocketEventType } from "./foo/bar.js";
import mediasoup from "mediasoup";
import { RouterOptions, RouterRtpCodecCapability } from "mediasoup/types";

const app = express();
const server = http.createServer(app);
const webSockerServer = new WebSocketServer({ server, path: "/ws" });

app.get("/", (req: Request, res: Response) => {
  res.send("Hello, Express!");
});

const worker = await mediasoup.createWorker();

const audioCapabilities: RouterRtpCodecCapability = {
   kind: "audio",
   mimeType: "audio/opus",
   clockRate: 48000,
   channels: 2
}

const videoCapabilities: RouterRtpCodecCapability = {
   kind: "video",
   mimeType: "video/H264",
   clockRate: 90000,
   parameters: {
      "packetization-mode": 1,
      "profile-level-id": "42e01f",
      "level-asymmetry-allowed": 1
   }
}

const routerOptions: RouterOptions = {
   mediaCodecs: [
      audioCapabilities,
      videoCapabilities
   ],
   appData: undefined
}

const router = await worker.createRouter(routerOptions);

webSockerServer.on("connection", socket => {
  console.log("New client connected");

  socket.on("message", (message: string) => {
    const event = JSON.parse(message) as WebSocketEvent;
    switch (event.type) {
      case WebSocketEventType.ROUTER_RTP_CAPABILITIES_REQUEST:
        const data: WebSocketEvent = {
          type: WebSocketEventType.ROUTER_RTP_CAPABILITIES_RESPONSE,
          data: router.rtpCapabilities
        }
        socket.send(JSON.stringify(data));
        break;
    
      default:
        break;
    }
  });

  socket.on("close", () => {
    console.log("Client disconnected");
  });
}); 

server.listen(3000, () => {
   console.log("Server is running on: http://localhost:3000");
});