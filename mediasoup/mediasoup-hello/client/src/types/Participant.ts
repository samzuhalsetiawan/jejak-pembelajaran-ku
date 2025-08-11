import type { Transport } from "mediasoup-client/types"

export type Participant = {
   readonly id: string
   readonly transport: Transport[]
}