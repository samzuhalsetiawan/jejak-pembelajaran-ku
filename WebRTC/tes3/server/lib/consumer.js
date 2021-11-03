const { WebRtcTransport } = require("mediasoup/node/lib/WebRtcTransport");
const { Socket } = require("socket.io");

/**
 * 
 * @param {Socket} socket
 * @param {WebRtcTransport} consumerTransport 
 * @param {String} producerId
 * @param {import("mediasoup/node/lib/RtpParameters").RtpCapabilities} rtpCapabilities
 */
async function createConsumer(socket, consumerTransport, producerId, rtpCapabilities) {
  const consumer = await consumerTransport.consume({ producerId, rtpCapabilities, paused: true });
  socket.emit("consumer-created", {
    id: consumer.id,
    producerId: consumer.producerId,
    kind: consumer.kind,
    rtpParameters: consumer.rtpParameters,
    transportId: consumerTransport.id
  });
  socket.on("resume-consumer", async () => {
    await consumer.resume();
    socket.emit("consumer-resumed");
  });
  return consumer;
}

module.exports = { createConsumer }