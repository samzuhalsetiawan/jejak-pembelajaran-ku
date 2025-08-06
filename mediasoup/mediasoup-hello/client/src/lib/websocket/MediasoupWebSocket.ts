import { WebSocketConnectionError } from "./WebSocketConnectionError";

export class MediasoupWebSocket {

   public readonly url = MediasoupWebSocket.URL;

   private readonly webSocket: WebSocket;

   private constructor(
      webSocket: WebSocket
   ) {
      this.webSocket = webSocket;
   }
   
   public static readonly URL = "http://localhost:3000/ws";
   
   private static INSTANCE: MediasoupWebSocket | undefined = undefined;

   public static async getInstance(): Promise<MediasoupWebSocket> {
      if (this.INSTANCE instanceof MediasoupWebSocket) return Promise.resolve(this.INSTANCE)
      return new Promise((resolve, reject) => {
         const webSocket = new WebSocket(MediasoupWebSocket.URL)
         
         const onOpenListener = (event: WebSocketEventMap["open"]) => {
            console.log("WebSocket connection established");
            const mediasoupWebSocket = new MediasoupWebSocket(webSocket);
            this.INSTANCE = mediasoupWebSocket
            webSocket.removeEventListener("open", onOpenListener);
            webSocket.removeEventListener("error", onErrorListener);
            resolve(mediasoupWebSocket);
         }

         const onErrorListener = (event: WebSocketEventMap["error"]) => {
            const error = new WebSocketConnectionError();
            console.error(error);
            webSocket.removeEventListener("error", onErrorListener);
            webSocket.removeEventListener("open", onOpenListener);
            reject(error)
         }

         webSocket.addEventListener("open", onOpenListener);
         webSocket.addEventListener("error", onErrorListener);
      })
   }
}