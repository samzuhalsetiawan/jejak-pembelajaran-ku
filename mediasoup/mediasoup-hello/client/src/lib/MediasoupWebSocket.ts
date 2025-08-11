export type MediasoupWebSocketEventType =
   | "REQUEST_ROUTER_RTP_CAPABILITIES"
   | "ROUTER_RTP_CAPABILITIES_RECEIVED"
   | "UPDATE_DEVICE_RTP_CAPABILITIES"
   | "DEVICE_RTP_CAPABILITIES_UPDATED"
   | "CREATE_ROUTER_WEB_RTC_TRANSPORT"
   | "ROUTER_WEB_RTC_TRANSPORT_CREATED"
   | "CONNECT_TRANSPORT"
   | "TRANSPORT_CONNECTED"
   | "CREATE_ROUTER_TRANSPORT_PRODUCER"
   | "ROUTER_TRANSPORT_PRODUCER_CREATED"
   | "NEW_PARTICIPANT_JOINED"
   | "PARTICIPANT_LEFT"

export type MediasoupWebSocketEvent<T> = {
   type: MediasoupWebSocketEventType,
   data: T
}

export class MediasoupWebSocket {
   private webSocket: WebSocket;

   private constructor(url: string) {
      this.webSocket = new WebSocket(url);
   }

   public static async initialize(url: string): Promise<MediasoupWebSocket> {
      return new Promise((resolve, reject) => {
         const instance = new MediasoupWebSocket(url);
         instance.webSocket.onopen = () => {
            resolve(instance);
            instance.webSocket.onopen = null;
            instance.webSocket.onerror = null;
         };
         instance.webSocket.onerror = () => {
            reject(new Error("Failed to initialize websocket connection"));
            instance.webSocket.onopen = null;
            instance.webSocket.onerror = null;
         };
      });
   }

   public on<T>(eventType: MediasoupWebSocketEventType, callback: (data: T) => void) {
      this.webSocket.addEventListener("message", (messageEvent: MessageEvent) => {
         const event = JSON.parse(messageEvent.data) as MediasoupWebSocketEvent<T>;
            if (event.type === eventType) {
            callback(event.data);
         }
      });
   }

   public once<T>(eventType: MediasoupWebSocketEventType, callback: (data: T) => void) {
      this.webSocket.addEventListener("message", (messageEvent: MessageEvent) => {
         const event = JSON.parse(messageEvent.data) as MediasoupWebSocketEvent<T>;
            if (event.type === eventType) {
            callback(event.data);
         }
      }, { once: true });
   }

   public sendEvent<T>(eventType: MediasoupWebSocketEventType, data: T | null = null) {
      const event: MediasoupWebSocketEvent<T | null> = { type: eventType, data: data };
      this.webSocket.send(JSON.stringify(event));
   }
}