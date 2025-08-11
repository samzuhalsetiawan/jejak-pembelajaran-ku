import { Device, Transport, type DtlsParameters, type IceCandidate, type IceParameters, type MediaKind, type RtpCapabilities, type RtpParameters } from "mediasoup-client/types";
import { MediasoupWebSocket } from "./MediasoupWebSocket";

export class MediasoupClient {

   private constructor(
      private device: Device,
      private webSocket: MediasoupWebSocket
   ) {}

   public static async initialize(mediasoupWebSocket: MediasoupWebSocket): Promise<MediasoupClient> {
      try {
         const device = await Device.factory();
         return Promise.resolve(new MediasoupClient(device, mediasoupWebSocket));
      } catch (error) {
         return Promise.reject(error);
      }
   }

   public async loadDevice(): Promise<void> {
      return new Promise((resolve, reject) => {
         try {
            this.webSocket.sendEvent("REQUEST_ROUTER_RTP_CAPABILITIES");
            this.webSocket.once("ROUTER_RTP_CAPABILITIES_RECEIVED", async (routerRtpCapabilities: RtpCapabilities) => {
               await this.device.load({ routerRtpCapabilities });
               this.webSocket.sendEvent("UPDATE_DEVICE_RTP_CAPABILITIES", this.device.rtpCapabilities);
               this.webSocket.once("DEVICE_RTP_CAPABILITIES_UPDATED", () => {
                  resolve();
               });
            })
         } catch (error: any) {
            reject(error)
         }
      })
   }

   public async createWebRtcSendTransport(): Promise<Transport> {
      return new Promise((resolve, reject) => {
         try {
            this.webSocket.sendEvent("CREATE_ROUTER_WEB_RTC_TRANSPORT");
            this.webSocket.once("ROUTER_WEB_RTC_TRANSPORT_CREATED", async (routerTransport: RouterTransport) => {
               const sendTransport = this.device.createSendTransport(routerTransport);
               resolve(sendTransport);
            });
         } catch (error: any) {
            reject(error);
         }
      });
   }

   public async createRecvTransport(): Promise<Transport> {
      return new Promise((resolve, reject) => {
         try {
            this.webSocket.sendEvent("CREATE_ROUTER_WEB_RTC_TRANSPORT");
            this.webSocket.once("ROUTER_WEB_RTC_TRANSPORT_CREATED", async (routerTransport: RouterTransport) => {
               const sendTransport = this.device.createRecvTransport(routerTransport);
               resolve(sendTransport);
            });
         } catch (error: any) {
            reject(error);
         }
      });
   }

   public async connectTransport(transportId: string, dtlsParameters: DtlsParameters): Promise<void> {
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

   public async createRouterTransportProducer(param: {
      transportId: string,
      kind: MediaKind,
      rtpParameters: RtpParameters
   }): Promise<string> {
      return new Promise((resolve, reject) => {
         try {
            this.webSocket.sendEvent("CREATE_ROUTER_TRANSPORT_PRODUCER", param);
            this.webSocket.once("ROUTER_TRANSPORT_PRODUCER_CREATED", (routerTransportProducerId: string) => {
               resolve(routerTransportProducerId);
            });
         } catch (error: any) {
            reject(error);
         }
      });
   }
}

type RouterTransport = {
   id: string,
   iceParameters: IceParameters,
   iceCandidates: IceCandidate[],
   dtlsParameters: DtlsParameters
}