import { RtpCapabilities } from "mediasoup-client/lib/RtpParameters";
import { initializeDevice, initializeWebSocket, requestRouterRtpCapabilities } from "./lib/initialize";
import { WebSocketEvent, WebSocketEventType } from "./types/websocket";

async function main() {

   const device = await initializeDevice();

   const webSocket = await initializeWebSocket();

   requestRouterRtpCapabilities(webSocket);

   webSocket.onmessage = async (messageEvent: MessageEvent) => {
      const event = JSON.parse(messageEvent.data) as WebSocketEvent;
      switch (event.type) {
         case WebSocketEventType.ROUTER_RTP_CAPABILITIES_RESPONSE:
            await device.load({
               routerRtpCapabilities: event.data as RtpCapabilities
            })
            console.log(`device loaded: ${device.loaded}`);
            break;
         default:
            break;
      }
   }

}

main();