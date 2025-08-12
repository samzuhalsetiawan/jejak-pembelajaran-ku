import { Device, Transport, type AppData, type DtlsParameters, type IceCandidate, type IceParameters, type MediaKind, type RtpCapabilities, type RtpParameters } from "mediasoup-client/types";
import { MediasoupWebSocket } from "./MediasoupWebSocket";
import type { MediasoupEventData } from "./MediasoupEvent";


export class MediasoupClient {
   private readonly consumerTransports: Map<string, Transport> = new Map();

   private constructor(
      private readonly device: Device,
      private readonly webSocket: MediasoupWebSocket,
      public readonly sendTransport: Transport,
   ) {
      sendTransport.on("connect", (data, callback, errback) => { this.onSendTransportConnect(data, callback, errback) });
      sendTransport.on("produce", (data, callback, errback) => { this.onSendTransportProduce(data, callback, errback) });
      webSocket.on("NEW_PARTICIPANT_JOINED", data => { this.onNewParticipantJoined(data) });
      webSocket.on("PARTICIPANT_LEFT", data => { this.onParticipantLeft(data) });
      webSocket.on("CREATE_CONSUMER", data => { this.onCreateConsumer(data) });
   }

   private async onSendTransportConnect(
      { dtlsParameters }: { dtlsParameters: DtlsParameters },
      callback: () => void,
      errback: (error: Error) => void
   ) {
      try {
         await this.connectTransport(this.sendTransport.id, dtlsParameters);
         callback();
      } catch (error: any) {
         errback(error);
      }
   }

   private async onSendTransportProduce(
      { kind, rtpParameters, appData }: { 
         kind: MediaKind,
         rtpParameters: RtpParameters,
         appData: AppData
      },
      callback: (data: { id: string }) => void,
      errback: (error: Error) => void
   ) {
      try {
         const transportId = this.sendTransport.id;
         const producerId = await this.createProducer(transportId, kind, rtpParameters);
         callback({ id: producerId })
      } catch (error: any) {
         errback(error)
      }
   }

   private async onNewParticipantJoined(
      { participantId }: MediasoupEventData<"NEW_PARTICIPANT_JOINED">
   ) {
      const transport = await this.createRecvTransport(participantId);
      transport.on("connect", async (data: { dtlsParameters: DtlsParameters }, callback, errback) => {
         try {
            await this.connectTransport(transport.id, data.dtlsParameters);
            callback();
         } catch (error: any) {
            errback(error);
         }
      });
   }

   private onParticipantLeft(
      { participantId }: MediasoupEventData<"PARTICIPANT_LEFT">
   ) {
      this.consumerTransports.delete(participantId);
      this.onCleanUI?.call(undefined, participantId);
   }

   public onCleanUI: ((participantId: string) => void) | null = null

   private async onCreateConsumer(
      { participantId, consumerId, kind, producerId, rtpParameters }: MediasoupEventData<"CREATE_CONSUMER">
   ) {
      const consumerTransport = this.consumerTransports.get(participantId)!!;
      const consumer = await consumerTransport.consume({ id: consumerId, producerId, kind, rtpParameters });
      consumer.resume();
      const { track } = consumer;
      this.onNewTrack?.call(undefined, track, participantId);
   }

   public onNewTrack: ((track: MediaStreamTrack, participantId: string) => void) | null = null

   public static async initialize(mediasoupWebSocket: MediasoupWebSocket): Promise<MediasoupClient> {
      try {
         const device = await Device.factory();
         await this.loadDevice.bind(device)(mediasoupWebSocket);
         const sendTransport = await this.createSendTransport.bind(device)(mediasoupWebSocket);
         const mediasoupClient = new MediasoupClient(device, mediasoupWebSocket, sendTransport);
         return Promise.resolve(mediasoupClient);
      } catch (error) {
         return Promise.reject(error);
      }
   }

   private static async loadDevice(
      this: Device,
      webSocket: MediasoupWebSocket
   ): Promise<void> {
      return new Promise((resolve, reject) => {
         try {
            webSocket.sendEvent("SYNC_RTP_CAPABILITIES", null);
            webSocket.once("UPDATE_ROUTER_RTP_CAPABILITIES", async ({ routerRtpCapabilities }) => {
               await this.load({ routerRtpCapabilities });
               webSocket.sendEvent("UPDATE_DEVICE_RTP_CAPABILITIES", { deviceRtpCapabilities: this.rtpCapabilities });
               webSocket.once("RTP_CAPABILITIES_SYNCED", () => resolve());
            });
         } catch (error: any) {
            reject(error)
         }
      })
   }

   private static async createSendTransport(
      this: Device,
      webSocket: MediasoupWebSocket
   ): Promise<Transport> {
      return new Promise((resolve, reject) => {
         try {
            webSocket.sendEvent("CREATE_PRODUCER_TRANSPORT", null);
            webSocket.once("PRODUCER_TRANSPORT_CREATED", ({ id, iceCandidates, iceParameters, dtlsParameters }) => {
               const sendTransport = this.createSendTransport({ id, iceCandidates, iceParameters, dtlsParameters });
               resolve(sendTransport);
            });
         } catch (error: any) {
            reject(error);
         }
      });
   }

   private async connectTransport(transportId: string, dtlsParameters: DtlsParameters): Promise<void> {
      return new Promise((resolve, reject) => {
         try {
            this.webSocket.sendEvent("CONNECT_TRANSPORT", { transportId, dtlsParameters });
            this.webSocket.once("TRANSPORT_CONNECTED", () => {
               console.log(`Transport Connected: ${transportId}`);
               resolve();
            });
         } catch (error: any) {
            console.error(`Error connecting transport ${transportId}:`, error);
            reject(error);
         }
      });
   }

   private async createProducer(
      transportId: string,
      kind: MediaKind,
      rtpParameters: RtpParameters
   ): Promise<string> {
      return new Promise((resolve, reject) => {
         try {
            this.webSocket.sendEvent("CREATE_PRODUCER", { transportId, kind, rtpParameters });
            this.webSocket.once("PRODUCER_CREATED", ({ producerId }) => {
               resolve(producerId);
            });
         } catch (error: any) {
            reject(error);
         }
      });
   }

   private async createRecvTransport(producerParticipantId: string): Promise<Transport> {
      return new Promise((resolve, reject) => {
         try {
            this.webSocket.sendEvent("CREATE_CONSUMER_TRANSPORT", { producerParticipantId });
            this.webSocket.once("CONSUMER_TRANSPORT_CREATED", ({ id, iceCandidates, iceParameters, dtlsParameters }) => {
               const recvTransport = this.device.createRecvTransport({ id, iceCandidates, iceParameters, dtlsParameters });
               this.consumerTransports.set(producerParticipantId, recvTransport);
               resolve(recvTransport);
            });
         } catch (error: any) {
            reject(error);
         }
      });
   }
}