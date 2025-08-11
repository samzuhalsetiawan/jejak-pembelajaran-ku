import type { RouterOptions, WebRtcTransportOptions } from "mediasoup/types";

export const MediasoupConfiguration = {
   router: {
      mediaCodecs: [
         {
            kind: "audio",
            mimeType: "audio/opus",
            clockRate: 48000,
            channels: 2
         },
         {
            kind: "video",
            mimeType: "video/H264",
            clockRate: 90000,
            parameters: {
               "packetization-mode": 1,
               "profile-level-id": "42e01f",
               "level-asymmetry-allowed": 1
            }
         }
      ]
   } as RouterOptions,
   transport: {
      listenInfos: [
         {
            protocol: "udp",
            ip: "192.168.100.4",
            announcedAddress: undefined,
            portRange: {
               min: 3100,
               max: 3200
            }
         }
      ]
   } as WebRtcTransportOptions
}