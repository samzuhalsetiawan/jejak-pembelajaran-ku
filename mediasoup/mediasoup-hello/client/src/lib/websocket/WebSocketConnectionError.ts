export class WebSocketConnectionError extends Error {
   constructor(message: string = "WebSocket connection error") {
      super(message);
      this.name = "WebSocketConnectionError";
   }
}