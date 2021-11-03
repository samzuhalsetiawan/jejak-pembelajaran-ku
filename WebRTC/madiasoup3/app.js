const express = require("express");
const app = express();
const http = require("http");
const server = http.createServer(app);
const { Server } = require("socket.io");
const io = new Server(server);
const { v4: uuidV4 } = require("uuid");
const PORT = process.env.PORT || 3000;
const mediasoup = require("mediasoup");
const { config } = require("./config");
const { error } = require("console");

app.use(express.static('public'));
app.set('view engine', 'ejs');

// mediasoup
let nextMediasoupWorkerIdx = 0;
let mediasoupRouter;
let producerTransport;
let consumerTransport;
let producer;
let consumer;

const createWorker = async () => {
    const worker = await mediasoup.createWorker({
        logLevel: config.mediasoup.worker.logLevel,
        logTags: config.mediasoup.worker.logTags,
        rtcMinPort: config.mediasoup.worker.rtcMinPort,
        rtcMaxPort: config.mediasoup.worker.rtcMaxPort
    });

    worker.on("died", () => {
        console.error('mediasoup worker died, exiting in 2 second ... [pid:&d]', worker.pid);
        setTimeout(() => {
            process.exit(1);
        }, 2000);
    });

    const mediaCodecs = config.mediasoup.router.mediaCodecs;
    const mediasoupRouter = await worker.createRouter({ mediaCodecs });
    return mediasoupRouter;
}

app.get("/", (req, res) => {
    res.redirect(`/${uuidV4()}`);
});

app.get("/:room", (req, res) => {
    res.render("index", { roomID: req.params.room });
});

const createMediasoupRouter = async () => {
    try {
        mediasoupRouter = await createWorker();
    } catch (err) {
        throw error;
    }
}
createMediasoupRouter();

const createWebRTCTransport = async (msRouter) => {
    const {
        maxIncomeBitrate,
        initialAvailableOutgoingBitrate
    } = config.mediasoup.webRtcTransport;

    const transport = await msRouter.createWebRtcTransport({
        listenIps: config.mediasoup.webRtcTransport.listenIps,
        enableUdp: true,
        enableTcp: true,
        preferUdp: true,
        initialAvailableOutgoingBitrate
    });

    if (maxIncomeBitrate) {
        try {
            await transport.setMaxIncomingBitrate(maxIncomeBitrate);
        } catch (err) {
            console.error(err);
        }
    }

    return {
        transport,
        params: {
            id: transport.id,
            iceParameters: transport.iceParameters,
            iceCandidates: transport.iceCandidates,
            dtlsParameters: transport.dtlsParameters
        }
    }

}

io.on("connection", socket => {
    socket.emit("routerCapabilities", mediasoupRouter.rtpCapabilities);
    socket.on("joinRoom", ({ roomID }) => {
        socket.join(roomID);
        socket.on("produce", async ({ transportId, kind, rtpParameters }) => {
            producer = await producerTransport.produce({ kind, rtpParameters });
            socket.emit("produced", producer.id);
            socket.broadcast.to(roomID).emit("newProducer", "new user");
        });
    });
    socket.on("createProducerTransport", async ({ forceTcp, rtpCapabilities }) => {
        try {
            const { transport, params } = await createWebRTCTransport(mediasoupRouter);
            producerTransport = transport;
            socket.emit("producerTansportCreated", params);
        } catch (error) {
            console.error(error);
        }
    });
    socket.on("connectProducerTransport", async (dtlsParameters) => {
        await producerTransport.connect({ dtlsParameters });
        socket.emit("producerConnected", "producer connected!");
    });
    socket.on("createConsumeTransport", async ({ forceTcp }) => {
        try {
            const { transport, params } = await createWebRTCTransport(mediasoupRouter);
            consumerTransport = transport;
            socket.emit("consumerTransportCreated", params);
        } catch (error) {
            console.log(error);
        }
    });
    socket.on("connectConsumerTransport", async ({ transportID, dtlsParameters }) => {
        await consumerTransport.connect({ dtlsParameters });
        socket.emit("consumerTransportConnected");
    });
    socket.on("resume", async () => {
        await consumer.resume();
    });
    socket.on("consume", async ({ rtpCapabilities }) => {
        const res = await createConsumer(producer, rtpCapabilities);
        socket.emit("consumed", res);
    });
});

const createConsumer = async (producer, rtpCapabilities) => {
    if (!mediasoupRouter.canConsume({ producerId: producer.id, rtpCapabilities })) {
        console.error("cannot consume!");
        return;
    }

    try {
        consumer = await consumerTransport.consume({
            producerId: producer.id,
            rtpCapabilities,
            paused: producer.kind == "video"
        });
    } catch (err) {
        console.error("consume error: ", err);
        return;
    }

    return {
        producerId: producer.id,
        id: consumer.id,
        kind: consumer.kind,
        rtpParameters: consumer.rtpParameters,
        type: consumer.type,
        producerPaused: consumer.producerPaused
    }

}

server.listen(PORT, () => {
    console.log(`Server is listening in port: ${PORT}`);
});