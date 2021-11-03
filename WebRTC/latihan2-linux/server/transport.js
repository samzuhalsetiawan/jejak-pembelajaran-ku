const { config } = require("./config");
const { addTransportDebug } = require("./debug");
const { onProducer } = require("./producer");
const { createConsumer } = require("./consumer");

let mediasoupProducerTransports = {};
let mediasoupConsumerTransports = {}

async function createTransport(router, listenIps) {
    const transport = await router.createWebRtcTransport({
        listenIps,
        enableUdp: true,
        enableTcp: true,
        preferUdp: true,
        initialAvaliableOutgoingBitrate:
            config.mediasoup.webRtcTransport.initialAvailableOutgoingBitrate
    });

    if (config.mediasoup.webRtcTransport.maxIncomingBitrate) {
        try {
            await transport.setMaxIncomingBitrate(config.mediasoup.webRtcTransport.maxIncomingBitrate);
        } catch (err) {
            console.log(err);
        }
    }

    return transport
}

async function createProducerTransport(socket, router, listenIps) {
    const producerTransport = await createTransport(router, listenIps);
    producerTransport.socketID = socket.id;
    producerTransport.roomID = router.roomID;
    socket.emit("create-send-transport", {
        id: producerTransport.id,
        iceParameters: producerTransport.iceParameters,
        iceCandidates: producerTransport.iceCandidates,
        dtlsParameters: producerTransport.dtlsParameters
    });
    socket.on("connect-producer-transport", async dtlsParameters => {
        await producerTransport.connect({ dtlsParameters });
        socket.emit("producer-transport-connected");
    });
    producerTransport.observer.on("newproducer", producer => {
        onProducer(socket, router.roomID, producer.id, producerTransport.id);
    });

    if (mediasoupProducerTransports[router.roomID]) {
        mediasoupProducerTransports[router.roomID].push(producerTransport);
    } else {
        mediasoupProducerTransports[router.roomID] = [producerTransport];
    }
    addTransportDebug(router.id, socket.id, producerTransport.id, "send");
    return producerTransport;
}

async function createConsumerTransport(socket, router, listenIps, producerTransportID) {

    try {
        for (const transport of mediasoupConsumerTransports[router.roomID][socket.id]) {
            if (transport.producerTransportID === producerTransportID) return;
        }
    } catch (err) { }

    const consumerTransport = await createTransport(router, listenIps);
    consumerTransport.socketID = socket.id;
    consumerTransport.roomID = router.roomID;
    consumerTransport.producerTransportID = producerTransportID;
    socket.emit("create-recv-transport", {
        id: consumerTransport.id,
        iceParameters: consumerTransport.iceParameters,
        iceCandidates: consumerTransport.iceCandidates,
        dtlsParameters: consumerTransport.dtlsParameters
    });
    socket.on("connect-consumer-transport", async dtlsParameters => {
        await consumerTransport.connect({ dtlsParameters });
        socket.emit("consumer-transport-connected");
    });
    socket.on("create-consumer", async ({ producerID, rtpCapabilities, transportID }) => {
        await createConsumer(socket, producerID, rtpCapabilities, consumerTransport, transportID);
    });

    if (mediasoupConsumerTransports[router.roomID]) {
        if (mediasoupConsumerTransports[router.roomID][socket.id]) {
            mediasoupConsumerTransports[router.roomID][socket.id].push(consumerTransport);
        } else {
            mediasoupConsumerTransports[router.roomID][socket.id] = [consumerTransport];
        }
    } else {
        mediasoupConsumerTransports[router.roomID] = { [socket.id]: [consumerTransport] };
    }

    addTransportDebug(router.id, socket.id, consumerTransport.id, "recv");
}

module.exports = {
    createProducerTransport,
    createConsumerTransport,
    createTransport,
    mediasoupConsumerTransports,
    mediasoupProducerTransports
}