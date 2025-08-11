import type { DtlsParameters, IceCandidate, IceParameters, MediaKind, Router, RtpCapabilities, RtpParameters, Worker } from "mediasoup/types";
import { Participant } from "../types/Participant.js";
import { WebSocket, WebSocketServer } from "ws";
import mediasoup from "mediasoup";
import { MediasoupConfiguration } from "./MediasoupConfiguration.js";
import type { MediasoupEvent, MediasoupEventData, MediasoupEventName } from "./MediasoupEvent.js";

export class MediasoupServer {
   private readonly participants: Participant[] = [];

   private constructor(
      private readonly webSocketServer: WebSocketServer,
      private readonly worker: Worker,
      private readonly router: Router
   ) {
      webSocketServer.on("connection", this.onWebSocketConnection);
   }

   private onWebSocketConnection(socket: WebSocket) {
      socket.on("open", () => { this.addParticipant(socket) });
      socket.on("message", data => { this.onWebSocketMessage(socket, data) });
      socket.on("close", () => { this.removeParticipant(socket) });
   }

   private onWebSocketMessage(socket: WebSocket, rawData: WebSocket.RawData) {
      const event = JSON.parse(rawData.toString()) as MediasoupEvent
      switch (event.name) {
         case "SYNC_RTP_CAPABILITIES":
            this.onSyncRtpCapabilities(socket);
            break;
         case "UPDATE_DEVICE_RTP_CAPABILITIES":
            this.onUpdateDeviceRtpCapabilities(socket, event.data);
            break;
         case "CREATE_PRODUCER_TRANSPORT":
            this.onCreateProducerTransport(socket);
            break;
         case "CONNECT_TRANSPORT":
            this.onConnectTransport(socket, event.data);
            break;
         case "CREATE_PRODUCER":
            this.onCreateProducer(socket, event.data);

      }
   }

   private async onCreateProducer(
      socket: WebSocket,
      { transportId, kind, rtpParameters }: MediasoupEventData<"CREATE_PRODUCER">
   ) {
      const participant = this.participants.find(p => p.socket === socket)!!;
      const producer = await participant.producerTransport!!.produce({ kind, rtpParameters });
      this.sendEvent(socket, "PRODUCER_CREATED", { producerId: producer.id });
   }

   private async onConnectTransport(
      socket: WebSocket,
      { transportId, dtlsParameters }: MediasoupEventData<"CONNECT_TRANSPORT">
   ) {
      const participant = this.participants.find(p => p.socket === socket)!!;
      const transport = participant.getTransportById(transportId);
      await transport.connect({ dtlsParameters });
      this.sendEvent(socket, "TRANSPORT_CONNECTED", null);
   }

   private async onCreateProducerTransport(socket: WebSocket) {
      const transport = await this.router.createWebRtcTransport(MediasoupConfiguration.transport);
      const participant = this.participants.find(p => p.socket === socket)!!;
      participant.producerTransport = transport;
      this.sendEvent(socket, "PRODUCER_TRANSPORT_CREATED", {
         id: transport.id,
         iceParameters: transport.iceParameters,
         iceCandidates: transport.iceCandidates,
         dtlsParameters: transport.dtlsParameters
      });
   }

   private onUpdateDeviceRtpCapabilities(
      socket: WebSocket,
      { deviceRtpCapabilities }: MediasoupEventData<"UPDATE_DEVICE_RTP_CAPABILITIES">
   ) {
      const participant = this.participants.find(p => p.socket === socket)!!;
      participant.rtpCapabilities = deviceRtpCapabilities;
      this.sendEvent(socket, "RTP_CAPABILITIES_SYNCED", null)
   }

   private onSyncRtpCapabilities(socket: WebSocket) {
      this.sendEvent(socket, "UPDATE_ROUTER_RTP_CAPABILITIES", { routerRtpCapabilities: this.router.rtpCapabilities });
   }

   private sendEvent<K extends MediasoupEventName>(
      socket: WebSocket,
      eventName: K,
      data: MediasoupEventData<K>
   ) {
      const event = { name: eventName, data } as MediasoupEvent;
      socket.send(JSON.stringify(event));
   }

   private addParticipant(socket: WebSocket) {
      const participant = new Participant(socket);
      this.participants.push(participant);
   }

   private removeParticipant(socket: WebSocket) {
      const index = this.participants.findIndex(p => p.socket === socket);
      this.participants.splice(index, 1);
   }

   public static async initialize(webSocketServer: WebSocketServer): Promise<MediasoupServer> {
      return new Promise(async (resolve, reject) => {
         try {
            const worker = await mediasoup.createWorker();
            const router = await worker.createRouter(MediasoupConfiguration.router);
            resolve(new MediasoupServer(webSocketServer, worker, router));
         } catch (error: any) {
            reject(error)
         }
      });
   }

}