import { Device } from "mediasoup-client";
import { WebSocketEvent, WebSocketEventType } from "../types/websocket";

export async function initializeDevice(): Promise<Device> {
   try {
      const device = await  Device.factory();
      return Promise.resolve(device);
   } catch (error: any) {
      return Promise.reject(error);
   }
}

export async function initializeWebSocket(): Promise<WebSocket> {
   return new Promise((resolve, reject) => {
      const webSocket = new WebSocket("http://localhost:3000/ws");
      webSocket.onopen = () => {
         resolve(webSocket);
         webSocket.onopen = null;
         webSocket.onerror = null;
      }
      webSocket.onerror = () => {
         reject("Connection failed to websocket");
      }
   });
}

export function requestRouterRtpCapabilities(webSocket: WebSocket) {
   const data: WebSocketEvent = {
      type: WebSocketEventType.ROUTER_RTP_CAPABILITIES_REQUEST,
      data: null
   }
   webSocket.send(JSON.stringify(data));
}