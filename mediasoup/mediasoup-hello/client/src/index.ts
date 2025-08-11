import { Device } from "mediasoup-client";
import { MediasoupWebSocket } from "./lib/MediasoupWebSocket";
import type { RtpCapabilities } from "mediasoup-client/types";
import { MediasoupClient } from "./lib/MediasoupClient";
import type { Participant } from "./types/Participant";

const btnOpenCam = document.getElementById("btn-opencam");
const webSocketUrl = "http://localhost:3000/ws"

async function main() {

   const webSocket = await MediasoupWebSocket.initialize(webSocketUrl);

   const mediasoup = await MediasoupClient.initialize(webSocket);

   await mediasoup.loadDevice();

   const participants: Participant[] = [];

   const sendTransport = await mediasoup.createWebRtcSendTransport();

   console.log(`Send Transport Created: ${sendTransport.id}`);

   sendTransport.on("connect", async ({ dtlsParameters }, callback, errback) => {
      try {
         await mediasoup.connectTransport(sendTransport.id, dtlsParameters);
         console.log(`Send Transport Connected: ${sendTransport.id}`);
         callback();
      } catch (error: any) {
         console.error(error);
         errback(error);
      }
   });

   sendTransport.on("produce", async (parameters, callback, errback) => {
      try {
         const routerTransportProducerId = await mediasoup.createRouterTransportProducer({
            transportId: sendTransport.id,
            kind: parameters.kind,
            rtpParameters: parameters.rtpParameters
         });
         callback({ id: routerTransportProducerId })
      } catch (error: any) {
         errback(error);
      }
   });

   webSocket.on("NEW_PARTICIPANT_JOINED", async (participantId: string) => {
      const recvTransport = await mediasoup.createRecvTransport();

      console.log(`Recv Transport Created: ${recvTransport.id}`);

      recvTransport.on("connect", async ({ dtlsParameters }, callback, errback) => {
         console.log(`Connecting Recv Transport: ${recvTransport.id}`);
         try {
            await mediasoup.connectTransport(recvTransport.id, dtlsParameters);
            console.log(`Recv Transport Connected: ${recvTransport.id}`);
            callback();
         } catch (error: any) {
            console.error(error);
            errback(error);
         }
      });
      
      const participant: Participant = {
         id: participantId,
         transport: [recvTransport]
      }
      participants.push(participant);
   });

   webSocket.on("PARTICIPANT_LEFT", (participantId: string) => {
      const participant = participants.find(participant => participant.id === participantId)!!;
      participant.transport.forEach(transport => { transport.close() });
      participants.slice(participants.indexOf(participant), 1);
   });

   btnOpenCam?.addEventListener("click", async () => {
      console.log("clicked")
      const stream = await navigator.mediaDevices.getUserMedia({ video: true });
      const videoTrack = stream.getVideoTracks()[0];
      const producer = await sendTransport.produce({
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
