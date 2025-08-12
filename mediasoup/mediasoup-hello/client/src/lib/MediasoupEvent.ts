import type { DtlsParameters, IceCandidate, IceParameters, MediaKind, RtpCapabilities, RtpParameters } from "mediasoup-client/types";

export type MediasoupEventName = Pick<MediasoupEvent, "name">["name"];

export type MediasoupEventData<K extends MediasoupEventName> = Pick<Extract<MediasoupEvent, { name: K }>, "data">["data"]

export type MediasoupEvent =
   { 
      name: "SYNC_RTP_CAPABILITIES",
      data: null
   }
   |{ 
      name: "UPDATE_ROUTER_RTP_CAPABILITIES",
      data: {
         routerRtpCapabilities: RtpCapabilities
      }
   }
   |{ 
      name: "UPDATE_DEVICE_RTP_CAPABILITIES",
      data: {
         deviceRtpCapabilities: RtpCapabilities
      }
   }
   |{ 
      name: "RTP_CAPABILITIES_SYNCED",
      data: null
   }
   |{ 
      name: "CREATE_PRODUCER_TRANSPORT",
      data: null
   }
   |{ 
      name: "PRODUCER_TRANSPORT_CREATED",
      data: {
         id: string,
         iceParameters: IceParameters,
         iceCandidates: IceCandidate[],
         dtlsParameters: DtlsParameters
      }
   }
   |{ 
      name: "CREATE_CONSUMER_TRANSPORT",
      data: {
         producerParticipantId: string
      }
   }
   |{ 
      name: "CONSUMER_TRANSPORT_CREATED",
      data: {
         id: string,
         iceParameters: IceParameters,
         iceCandidates: IceCandidate[],
         dtlsParameters: DtlsParameters
      }
   }
   |{ 
      name: "CONNECT_TRANSPORT",
      data: { 
         transportId: string,
         dtlsParameters: DtlsParameters
      }
   }
   |{ 
      name: "TRANSPORT_CONNECTED",
      data: null
   }
   |{ 
      name: "CREATE_PRODUCER",
      data: { 
         transportId: string,
         kind: MediaKind,
         rtpParameters: RtpParameters
      }
   }
   |{ 
      name: "PRODUCER_CREATED",
      data: {
         producerId: string
      }
   }
   |{ 
      name: "CREATE_CONSUMER",
      data: {
         participantId: string,
         consumerId: string,
         producerId: string,
         kind: MediaKind,
         rtpParameters: RtpParameters
      }
   }
   |{ 
      name: "NEW_PARTICIPANT_JOINED",
      data: {
         participantId: string
      }
   }
   |{ 
      name: "PARTICIPANT_LEFT",
      data: {
         participantId: string
      }
   }