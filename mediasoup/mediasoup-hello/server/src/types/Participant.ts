import type { RtpCapabilities, WebRtcTransport } from "mediasoup/types"
import { WebSocket } from "ws"
import { v4 as uuidV4 } from 'uuid';

export class Participant {
   public readonly id: string;

   constructor(
      public readonly socket: WebSocket,
      public rtpCapabilities?: RtpCapabilities,
      public producerTransport?: WebRtcTransport,
      public readonly consumerTransport: WebRtcTransport[] = []
   ) {
      this.id = uuidV4();
   }

   getTransportById(transportId: string): WebRtcTransport {
      const transport = this.producerTransport?.id === transportId ?
         this.producerTransport :
         this.consumerTransport.find(transport => transport.id === transportId);
      if (!transport) throw new Error("No Transport Found");
      return transport;
   }
}