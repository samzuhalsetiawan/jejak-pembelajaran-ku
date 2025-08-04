import { Device } from 'mediasoup-client';
import { TransportOptions } from 'mediasoup-client/lib/Transport';

async function main() {
   let device: Device;
   try {
      device = await Device.factory();
   } catch (error: any) {
      if (error.name === 'UnsupportedError') {
         console.warn('browser not supported');
      } else {
         console.error('Error creating mediasoup client device:', error);
      }
      throw error;
   }
   const routerRtpCapabilities = {
      codecs: [] // TODO: Get actual RTP capabilities from the server
   };
   await device.load({ routerRtpCapabilities });
   device.rtpCapabilities //TODO: These RTP capabilities must be given to the mediasoup router in order to consume a remote stream.

   // TODO: Get ICE Candidates and DTLS parameters from the server
   const sendTransportOptions: TransportOptions = {
      id: "",
      iceParameters: {
         usernameFragment: "",
         password: "",
         iceLite: false,
      },
      iceCandidates: [],
      dtlsParameters: {
         fingerprints: []
      },
   }

   // TODO: The transport must be previously created in the mediasoup router
   const sendTransport = device.createSendTransport(sendTransportOptions)
}

main().catch((error) => {
   console.error("Error in mediasoup client setup:", error);
});