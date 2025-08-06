import { Device } from "mediasoup-client"

export class MediasoupClient {

   private readonly device: Device;

   private constructor(
      device: Device
   ) {
      this.device = device;
   }

   static async initialize(): Promise<MediasoupClient> {
      let device: Device;
      try {
         device = await Device.factory();
      } catch (error: any) {
         return Promise.reject(error)
      }
      return Promise.resolve(new MediasoupClient(device))
   }

}