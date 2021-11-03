const { config } = require("../etc/config");

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

async function createProducerTransport(socket, listenIps) {
    const producerTransport = await createTransport(socket.router, listenIps);
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

    producerTransport.consumerTransports = new Set();
    producerTransport.producers = new Map();
    socket.producerTransport = producerTransport;
}

async function createConsumerTransport(socket, listenIps) {
    const consumerTransport = await createTransport(socket.router, listenIps);
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

    consumerTransport.consumers = new Map();
    socket.consumerTransports.add(consumerTransport);
}

module.exports = {
    createConsumerTransport,
    createProducerTransport
}