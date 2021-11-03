let mediasoupProducers = {};

async function createProducer(socket, transport, kind, rtpParameters) {
    const producer = await transport.produce({ kind, rtpParameters });
    producer.socketID = socket.id;
    producer.transportID = transport.id;
    producer.roomID = transport.roomID;

    if (mediasoupProducers[transport.roomID]) {
        mediasoupProducers[transport.roomID].push(producer);
    } else {
        mediasoupProducers[transport.roomID] = [producer];
    }

    socket.emit("producer-created", producer.id);
}

function onProducer(socket, roomID, producerID, transportID) {
    socket.emit("need-rtpCapabilities");
    socket.on("sended-rtpCapabilities", rtpCapabilities => {
        socket.broadcast.to(roomID).emit("new-producer", { producerID, rtpCapabilities, transportID });
    });
}

module.exports = { mediasoupProducers, createProducer, onProducer }