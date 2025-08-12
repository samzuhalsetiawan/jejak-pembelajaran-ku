import type { DtlsParameters, IceCandidate, IceParameters, MediaKind, Router, RtpCapabilities, RtpParameters, WebRtcTransport, Worker } from "mediasoup/types";
import { Participant } from "./Participant.js";
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
      webSocketServer.on("connection", socket => { this.onWebSocketConnection(socket) });
   }

   private onWebSocketConnection(socket: WebSocket) {
      this.addParticipant(socket);
      socket.on("message", data => { this.onWebSocketMessage(socket, data) });
      socket.on("close", () => { this.onWebSoketClose(socket) });
   }

   private onWebSoketClose(socket: WebSocket) {
      console.log("onclose");
      this.removeParticipant(socket);
   }

   private onWebSocketMessage(socket: WebSocket, rawData: any) {
      const event = JSON.parse(rawData) as MediasoupEvent
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
         case "CREATE_CONSUMER_TRANSPORT":
            this.onCreateConsumerTransport(socket, event.data);
            break;
         case "CONNECT_TRANSPORT":
            this.onConnectTransport(socket, event.data);
            break;
         case "CREATE_PRODUCER":
            this.onCreateProducer(socket, event.data);

      }
   }

   private onSyncRtpCapabilities(socket: WebSocket) {
      this.sendEvent(socket, "UPDATE_ROUTER_RTP_CAPABILITIES", { routerRtpCapabilities: this.router.rtpCapabilities });
   }

   private onUpdateDeviceRtpCapabilities(
      socket: WebSocket,
      { deviceRtpCapabilities }: MediasoupEventData<"UPDATE_DEVICE_RTP_CAPABILITIES">
   ) {
      const participant = this.participants.getParticipantBySocket(socket);
      participant.rtpCapabilities = deviceRtpCapabilities;
      this.sendEvent(socket, "RTP_CAPABILITIES_SYNCED", null)
   }

   private async createTransport(): Promise<WebRtcTransport> {
      try {
         const transport = await this.router.createWebRtcTransport(MediasoupConfiguration.transport);
         return Promise.resolve(transport);
      } catch (error: any) {
         return Promise.reject(error);
      }
   }

   private async onCreateProducerTransport(socket: WebSocket) {
      const transport = await this.createTransport();
      const participant = this.participants.getParticipantBySocket(socket);
      participant.producerTransport = transport;
      this.sendEvent(socket, "PRODUCER_TRANSPORT_CREATED", {
         id: transport.id,
         iceParameters: transport.iceParameters,
         iceCandidates: transport.iceCandidates,
         dtlsParameters: transport.dtlsParameters
      });
      this.broadcastNewParticipantJoined(participant);
   }

   private broadcastNewParticipantJoined(owner: Participant) {
      this.participants.forEach(participant => {
         if (participant !== owner) {
            this.sendEvent(participant.socket, "NEW_PARTICIPANT_JOINED", { participantId: owner.id });
         }
      });
   }

   private async onCreateConsumerTransport(socket: WebSocket, { producerParticipantId }: { producerParticipantId: string; }) {
      const transport = await this.createTransport();
      const participant = this.participants.getParticipantBySocket(socket);
      participant.consumerTransport.set(producerParticipantId, transport);
      this.sendEvent(socket, "CONSUMER_TRANSPORT_CREATED", {
         id: transport.id,
         iceParameters: transport.iceParameters,
         iceCandidates: transport.iceCandidates,
         dtlsParameters: transport.dtlsParameters
      });
   }

   private async onConnectTransport(
      socket: WebSocket,
      { transportId, dtlsParameters }: MediasoupEventData<"CONNECT_TRANSPORT">
   ) {
      const participant = this.participants.getParticipantBySocket(socket);
      const transport = participant.getTransportById(transportId);
      await transport.connect({ dtlsParameters });
      this.sendEvent(socket, "TRANSPORT_CONNECTED", null);
   }

   private async onCreateProducer(
      socket: WebSocket,
      { transportId, kind, rtpParameters }: MediasoupEventData<"CREATE_PRODUCER">
   ) {
      const participant = this.participants.getParticipantBySocket(socket);
      const producer = await participant.producerTransport.produce({ kind, rtpParameters });
      this.sendEvent(socket, "PRODUCER_CREATED", { producerId: producer.id });
      this.broadcastNewProducer(producer.id, participant);
   }

   private broadcastNewProducer(producerId: string, owner: Participant) {
      this.participants.forEach(async participant => {
         if (participant !== owner) {
            const consumer = await participant.consumerTransport.get(owner.id)!!.consume({
               producerId: producerId,
               rtpCapabilities: participant.rtpCapabilities,
               paused: false
            });
            this.sendEvent(participant.socket, "CREATE_CONSUMER", {
               participantId: owner.id,
               consumerId: consumer.id,
               producerId: producerId,
               kind: consumer.kind,
               rtpParameters: consumer.rtpParameters
            });
         }
      })
   }

   private addParticipant(socket: WebSocket) {
      const participant = new Participant(socket);
      this.participants.push(participant);
      console.log(`new participant: ${participant.id}`);
   }

   private removeParticipant(socket: WebSocket) {
      const leftParticipant = this.participants.getParticipantBySocket(socket);
      const index = this.participants.indexOf(leftParticipant);
      this.participants.splice(index, 1);
      this.participants.forEach(participant => {
         if (participant.socket !== socket) {
            this.sendEvent(participant.socket, "PARTICIPANT_LEFT", { 
               participantId: leftParticipant.id
            });
         }
      });
   }

   private sendEvent<K extends MediasoupEventName>(
      socket: WebSocket,
      eventName: K,
      data: MediasoupEventData<K>
   ) {
      const event = { name: eventName, data } as MediasoupEvent;
      socket.send(JSON.stringify(event));
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