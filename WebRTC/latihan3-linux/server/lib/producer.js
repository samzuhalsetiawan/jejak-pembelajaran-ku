async function createProducer(socket, kind, rtpParameters) {
  const producerTransport = socket.producerTransport;
  const producer = await producerTransport.produce({ kind, rtpParameters });
  producerTransport.producers.set(producer.kind, producer);
  socket.emit("producer-created", producer.id);
  socket.broadcast.to([...socket.rooms][1]).emit("new-producer-created", { producerId: producer.id, kind: producer.kind });
}

module.exports = { createProducer }