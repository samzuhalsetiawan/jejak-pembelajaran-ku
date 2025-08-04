import mediasoup from "mediasoup";
import { RouterOptions, RouterRtpCodecCapability, WebRtcTransportOptions } from "mediasoup/types";

const worker = await mediasoup.createWorker();

const audioCapabilities: RouterRtpCodecCapability = {
   kind: "audio",
   mimeType: "audio/opus",
   clockRate: 48000,
   channels: 2
}

const videoCapabilities: RouterRtpCodecCapability = {
   kind: "video",
   mimeType: "video/H264",
   clockRate: 90000,
   parameters: {
      "packetization-mode": 1,
      "profile-level-id": "42e01f",
      "level-asymmetry-allowed": 1
   }
}

const routerOptions: RouterOptions = {
   mediaCodecs: [
      audioCapabilities,
      videoCapabilities
   ],
   appData: undefined
}

const router = await worker.createRouter(routerOptions);

const webRtcTransportOptions: WebRtcTransportOptions = {
   listenInfos: []
}

const transport = await router.createWebRtcTransport(webRtcTransportOptions)
