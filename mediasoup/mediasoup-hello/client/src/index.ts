import { Device } from "mediasoup-client";
import { MediasoupWebSocket } from "./lib/MediasoupWebSocket";
import type { RtpCapabilities } from "mediasoup-client/types";
import { MediasoupClient } from "./lib/MediasoupClient";
import type { Participant } from "./types/Participant";

const btnOpenCam = document.getElementById("btn-opencam");
const webSocketUrl = "http://localhost:3000/ws"

async function main() {

   const webSocket = await MediasoupWebSocket.initialize(webSocketUrl);

   const mediasoupClient = await MediasoupClient.initialize(webSocket);

   mediasoupClient.onNewTrack = (track, participantId) => {
      const container = document.getElementById("videos");
      const videoElement = document.createElement("video");
      videoElement.id = participantId;
      videoElement.muted = true;
      // videoElement.pause();
      videoElement.srcObject = new MediaStream([ track ]);
      container?.appendChild(videoElement);
      videoElement.autoplay = true;
      // videoElement.play();
      console.log(track);
   }

   mediasoupClient.onCleanUI = participantId => {
      document.getElementById(participantId)?.remove();
   }

   btnOpenCam?.addEventListener("click", async () => {
      console.log("clicked")
      const stream = await navigator.mediaDevices.getUserMedia({ video: true });
      const videoTrack = stream.getVideoTracks()[0];
      const localVideo = document.getElementById("local") as HTMLVideoElement;
      localVideo.srcObject = stream;
      localVideo.play();
      const producer = await mediasoupClient.sendTransport.produce({
         track       : videoTrack,
         encodings   :
         [
            { maxBitrate: 100000 },
            { maxBitrate: 300000 },
            { maxBitrate: 900000 }
         ],
         codecOptions :
         {
            videoGoogleStartBitrate : 1000
         }
      });
      console.log(`Produced: ${producer.id}`);
   });
}

main();
