let mediasoupConsumers = {}

async function createConsumer(socket, producerId, rtpCapabilities, consumerTransport, transportID) {
    if (consumerTransport.producerTransportID !== transportID) return;
    const consumer = await consumerTransport.consume({ producerId, rtpCapabilities, paused: true });
    socket.emit("consumer-created", {
        id: consumer.id,
        producerId: consumer.producerId,
        kind: consumer.kind,
        rtpParameters: consumer.rtpParameters,
        consumerTransportID: consumerTransport.id
    });
    socket.on("resume-consumer", () => {
        consumer.resume();
        // socket.emit("consumer-resumed");
    });
}

module.exports = { mediasoupConsumers, createConsumer }