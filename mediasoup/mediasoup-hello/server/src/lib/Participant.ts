import type { RtpCapabilities, WebRtcTransport } from "mediasoup/types"
import { WebSocket } from "ws"
import { v4 as uuidV4 } from 'uuid';

declare global {
   interface Array<T extends Participant> {
      getParticipantBySocket(this: Array<Participant>, socket: WebSocket): Participant
      getParticipantIndexBySocket(this: Array<Participant>, socket: WebSocket): number
   }
}

Array.prototype.getParticipantBySocket = function (socket: WebSocket): Participant {
   const participant = this.find(participant => participant.socket === socket);
   if (!participant) throw new Error("No Participant Found");
   return participant;
}

Array.prototype.getParticipantIndexBySocket = function (socket: WebSocket): number {
   return this.indexOf(this.getParticipantBySocket(socket));
}

export class Participant {
   public readonly id: string;
   public readonly socket: WebSocket;
   public rtpCapabilities!: RtpCapabilities;
   public producerTransport!: WebRtcTransport;
   public readonly consumerTransport: Map<string, WebRtcTransport> = new Map();

   constructor(socket: WebSocket) {
      this.id = uuidV4();
      this.socket = socket;
   }

   getTransportById(transportId: string): WebRtcTransport {
      const transport = this.producerTransport.id === transportId ?
         this.producerTransport :
         [...this.consumerTransport.values()].find(transport => transport.id === transportId);
      if (!transport) throw new Error("No Transport Found");
      return transport;
   }
}