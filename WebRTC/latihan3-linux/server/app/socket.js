const { Server } = require("socket.io");
const { server } = require("../app/server");
const { joinRoom } = require("./utils/joinRoom");
const { config } = require("../etc/config");
const { debug, debugPause } = require("./utils/debug");
const {
    createConsumerTransport,
    createProducerTransport
} = require("../lib/transport");
const { createProducer } = require("../lib/producer");
const { createConsumer } = require("../lib/consumer");
const io = new Server(server);

function runSocket() {
    io.on("connection", socket => {
        socket.on("join-room", async roomId => {
            await joinRoom(socket, roomId);
            socket.emit("router-rtpCapabilities", socket.router.rtpCapabilities);
            socket.on("new-producer-transport-created", () => {
                socket.broadcast.to(roomId).emit("new-producer-transport");
            });
        });

        socket.on("client-device-loaded", async () => { //? Here IP Sended
            await createProducerTransport(
                socket,
                config.mediasoup.webRtcTransport.listenIps
            );
            socket.router.peserta.forEach(async (sock) => {
                if (socket.id === sock.id) return;
                await createConsumerTransport(
                    socket,
                    config.mediasoup.webRtcTransport.listenIps
                );
            });
            socket.on("create-consumer-transport", async () => {
                await createConsumerTransport(
                    socket,
                    config.mediasoup.webRtcTransport.listenIps
                );
            });
            socket.on("create-producer", async ({ kind, rtpParameters }) => {
                await createProducer(socket, kind, rtpParameters);
            });
            socket.on("create-consumer", async ({ producerId, rtpCapabilities, kind }) => {
                await createConsumer(socket, producerId, rtpCapabilities, kind);
            });
        });

        socket.on("debug", () => {
            debug(socket);
            debugPause(socket);
        });

    });
}

module.exports = { runSocket }