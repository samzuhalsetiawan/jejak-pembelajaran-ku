const express = require("express");
const app = express();
const http = require("http");
const server = http.createServer(app);
const { Server } = require("socket.io");
const path = require("path");
const io = new Server(server);
const { v4: uuidV4 } = require("uuid");
const mediasoup = require("mediasoup");
const PORT = process.env.PORT || 3000;
const { config } = require("./config");

//variable
const publicPath = path.resolve("../client/dist");
let mediasoupWorkers = [];
let mediasoupRooms = {};
let mediasoupProducers = [];
let mediasoupConsumerTransport = {};

//midleware
app.use(express.static(publicPath));

//mediasoup
// Buat Worker
createWorker();

async function createWorker() {
    for (let i = 0; i < config.mediasoup.numWorkers; i++) {
        const worker = await mediasoup.createWorker({
            logLevel: config.mediasoup.worker.logLevel,
            logTags: config.mediasoup.worker.logTags,
            rtcMinPort: config.mediasoup.worker.rtxMinPort,
            rtcMaxPort: config.mediasoup.worker.rtxMaxPort
        });
        worker.on("died", () => {
            console.error('Mediasoup worker died, exiting in 2 second ... [pid: &d]', worker.pid);
            setTimeout(() => {
                process.exit(1);
            }, 2000);
        });
        // Simpan worker yg telah dibuat ke variable global
        mediasoupWorkers.push({ worker, numRouter: 0 });
    }
}

// Function untuk mendapatkan dan memilih worker terbaik
// worker load balancer
function getWorker() {
    let mediasoupWorker;
    for (const value of mediasoupWorkers) {
        if (mediasoupWorker) {
            if (value.numRouter < mediasoupWorker.numRouter) mediasoupWorker = value;
        } else {
            mediasoupWorker = value;
        }
    }
    return mediasoupWorker.worker;
}

// function membuat transport
async function createTransport(router, listenIps) {
    const transport = await router.createWebRtcTransport({
        listenIps,
        enebleUdp: true,
        enableTcp: true,
        preferUdp: true,
        initialAvailableOutgoingBitrate:
            config.mediasoup.webRtcTransport.initialAvailableOutgoingBitrate
    });

    if (config.mediasoup.webRtcTransport.maxIncomeBitrate) {
        try {
            await transport.setMaxIncomingBitrate(config.mediasoup.webRtcTransport.maxIncomeBitrate);
        } catch (error) {
            console.log(error);
        }
    }
    return transport;
}

//function membuat consumer transport
async function createConsumerTransport(router, listenIps, socket) {
    for (const producer of mediasoupProducers) {
        console.log("Iterasi Sebanyak: ===> ", mediasoupProducers.length);
        const consumerTransport = await createTransport(router, listenIps);
        mediasoupConsumerTransport[producer.id] = consumerTransport;
        socket.emit("consumer-transport-created", {
            id: consumerTransport.id,
            iceParameters: consumerTransport.iceParameters,
            iceCandidates: consumerTransport.iceCandidates,
            dtlsParameters: consumerTransport.dtlsParameters
        });
        socket.on("connect-consumer-transport", async dtlsParameters => {
            await consumerTransport.connect({ dtlsParameters });
            console.log("Consumer Transport Connected <===>");
            socket.emit("consumer-transport-connected");
        });
        socket.on("consume-needed", async rtpCapabilities => {
            //? HERE WE GOT CONSUMER
            const consumer = await consumerTransport.consume({ producerId: producer.id, rtpCapabilities, paused: producer.kind == "video" });
            console.log(consumer);
            socket.emit("consumer-created", {
                id: consumer.id,
                producerId: producer.id,
                kind: consumer.kind,
                rtpParameters: consumer.rtpParameters
            });
            socket.on("resume-please", () => {
                consumer.resume();
            });
        });
    }
}

//express app
app.get("/", (req, res) => {
    const roomID = uuidV4();
    res.redirect(`/${roomID}`);
});
app.get("/:roomID", (req, res) => {
    res.sendFile(path.join(publicPath, "home.html"));
});

//socket io
io.on("connection", socket => {
    // Join ke room
    socket.on("join-room", async (roomID) => {
        socket.join(roomID);
        // cek apakah room sudah pernah dibuat sebelumnya
        if (mediasoupRooms[roomID]) {
            mediasoupRooms[roomID].peserta.push(socket);
        } else {
            const worker = getWorker();
            const router = await worker.createRouter({ mediaCodecs: config.mediasoup.router.mediaCodecs });
            mediasoupRooms[roomID] = { router, peserta: [socket] };
        }
        // kirim router rtpCapabilities to client
        socket.emit("router-rtpCapabilities", mediasoupRooms[roomID].router.rtpCapabilities);

        // buat producer transport
        const producerTransport = await createTransport(mediasoupRooms[roomID].router, config.mediasoup.webRtcTransport.listenIps);
        await createConsumerTransport(mediasoupRooms[roomID].router, config.mediasoup.webRtcTransport.listenIps, socket);
        socket.emit("producer-transport-created", {
            id: producerTransport.id,
            iceParameters: producerTransport.iceParameters,
            iceCandidates: producerTransport.iceCandidates,
            dtlsParameters: producerTransport.dtlsParameters
        });

        socket.on("connect-producer-transport", async dtlsParameters => {
            await producerTransport.connect({ dtlsParameters });
            socket.emit("producer-transport-connected");
        });

        socket.on("create-producer", async ({ kind, rtpParameters }) => {
            // ? HERE WE GOT PRODUCER
            const producer = await producerTransport.produce({ kind, rtpParameters });
            mediasoupProducers.push(producer);
            socket.emit("producer-created", producer.id);
            console.log("Producer dibuat ===> ", producer.kind);
            io.to(roomID).emit("debugging", JSON.stringify(mediasoupProducers));
            socket.broadcast.to(roomID).emit("new-producer-created", producer.id);
        });


        // TODO Consumer Processing


        // socket.broadcast.to(roomID).emit("new-user-join");
        // mediasoupRooms[roomID].peserta.push(socket)
        socket.on("disconnect", reason => {
            console.log(reason);
            // keluarkan socket dari variable
            const temp = mediasoupRooms[roomID].peserta.filter(peserta => peserta.connected);
            mediasoupRooms[roomID].peserta = temp;
            // cek apakah sudah tidak ada lagi peserta meeting
            if (mediasoupRooms[roomID].peserta.length <= 0) {
                mediasoupRooms[roomID].router.close();
                delete mediasoupRooms[roomID];
            }
        });
    });
});

server.listen(PORT, () => {
    console.log("Server Start");
});