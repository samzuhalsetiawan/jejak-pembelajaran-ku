export enum WebSocketEventType {
   ROUTER_RTP_CAPABILITIES_REQUEST,
   ROUTER_RTP_CAPABILITIES_RESPONSE
}

export type WebSocketEvent = {
   type: WebSocketEventType,
   data: any
}