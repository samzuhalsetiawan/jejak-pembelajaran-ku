async function createConsumer(socket, producerId, rtpCapabilities, kind) {
  let consumerTransport;
  for (const transport of socket.consumerTransports) {
    if (transport.consumers.size) continue;
    consumerTransport = transport;
  }
  const consumer = await consumerTransport.consume({ producerId, rtpCapabilities, paused: true });
  consumerTransport.consumers.set(consumer.kind, consumer);
  socket.emit("consumer-created", {
    id: consumer.id,
    producerId: consumer.producerId,
    kind: consumer.kind,
    rtpParameters: consumer.rtpParameters
  });
  socket.on("resume-consumer", async () => {
    await consumer.resume();
    socket.emit("consumer-resumed");
  });
}

module.exports = { createConsumer }
