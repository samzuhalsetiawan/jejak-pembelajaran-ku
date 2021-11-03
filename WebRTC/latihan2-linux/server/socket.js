const { config } = require("./config");
const { mediasoupRouters, createRoom } = require("./router");
const { createProducerTransport, createConsumerTransport, mediasoupProducerTransports } = require("./transport");
const { createProducer, mediasoupProducers } = require("./producer");
const { disconnect } = require("./disconnect");
const { transportDebugger } = require("./debug");
const { createConsumer } = require("./consumer");

async function websocket(io) {
    io.on("connection", socket => {
        socket.on("join-room", async roomID => {

            await createRoom(roomID, socket);

            socket.emit("router-rtpCapabilities", mediasoupRouters[roomID].rtpCapabilities);

            socket.on("client-device-loaded", async () => {//? here we need IP client
                const producerTransport = await createProducerTransport(socket, mediasoupRouters[roomID], config.mediasoup.webRtcTransport.listenIps);
                socket.on("create-producer", async ({ kind, rtpParameters }) => {
                    await createProducer(socket, producerTransport, kind, rtpParameters);
                });
                mediasoupProducerTransports[roomID].forEach(async (transport) => {
                    if (transport.socketID !== socket.id) {
                        // ? here we need IP Client
                        await createConsumerTransport(socket, mediasoupRouters[roomID], config.mediasoup.webRtcTransport.listenIps, transport.id);
                    }
                });
                if (mediasoupProducers[roomID]) {
                    mediasoupProducers[roomID].forEach((producer) => {
                        socket.emit("get-rtpCapabilities-then-create-consumer", { producerID: producer.id, transportID: producer.transportID });
                    });
                }
                socket.on("create-consumer-transport", async producerTransportID => {
                    await createConsumerTransport(socket, mediasoupRouters[roomID], config.mediasoup.webRtcTransport.listenIps, producerTransportID);
                });
            });
            socket.on("new-producer-transport-created", producerTransportID => {
                socket.broadcast.to(roomID).emit("new-producer-transport", producerTransportID);
            });

            socket.on("disconnect", () => {
                disconnect(roomID);
            });

            socket.on("debug", (roomID) => {
                // workerDebugger();
                // routerDebugger();
                transportDebugger({ json: true });
            });

        });

    });
}

module.exports = { websocket }