const { MediaKind, RtpParameters } = require("mediasoup/node/lib/RtpParameters");
const { WebRtcTransport } = require("mediasoup/node/lib/WebRtcTransport");
const { Socket } = require("socket.io");

/**
 * 
 * @param {WebRtcTransport} producerTransport 
 * @param {MediaKind} kind 
 * @param {RtpParameters} rtpParameters 
 * @param {Socket} socket
 */
async function createProducer(producerTransport, kind, rtpParameters, socket) {
  const producer = await producerTransport.produce({ kind, rtpParameters });
  socket.emit("producer-created", producer.id);
  return producer;
}

module.exports = { createProducer }