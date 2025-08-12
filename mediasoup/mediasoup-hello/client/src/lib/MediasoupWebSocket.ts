import type { MediasoupEvent, MediasoupEventData, MediasoupEventName } from "./MediasoupEvent";

export class MediasoupWebSocket {
   private webSocket: WebSocket;

   private constructor(url: string) {
      this.webSocket = new WebSocket(url);
   }

   public static async initialize(url: string): Promise<MediasoupWebSocket> {
      return new Promise((resolve, reject) => {
         const mediasoupWebSocket = new MediasoupWebSocket(url);
         const cleanUpListener = () => {
            mediasoupWebSocket.webSocket.onopen = null;
            mediasoupWebSocket.webSocket.onerror = null;
         }
         mediasoupWebSocket.webSocket.onopen = () => {
            cleanUpListener();
            resolve(mediasoupWebSocket);
         };
         mediasoupWebSocket.webSocket.onerror = () => {
            cleanUpListener()
            reject(new Error("Failed to initialize websocket connection"));
         };
      });
   }

   public on<K extends MediasoupEventName>(
      eventName: K,
      callback: (data: MediasoupEventData<K>) => void,
      once: boolean = false
   ) {
      this.webSocket.addEventListener("message", (messageEvent: MessageEvent) => {
         const event = JSON.parse(messageEvent.data) as MediasoupEvent;
            if (event.name === eventName) {
            callback(event.data as MediasoupEventData<K>);
         }
      }, { once });
   }

   public once<K extends MediasoupEventName>(
      eventName: K,
      callback: (data: MediasoupEventData<K>) => void
   ) {
      this.on(eventName, callback, true);
   }

   public sendEvent<K extends MediasoupEventName>(
      eventName: K,
      data: MediasoupEventData<K>
   ) {
      const event = { name: eventName, data } as MediasoupEvent;
      this.webSocket.send(JSON.stringify(event));
   }
}