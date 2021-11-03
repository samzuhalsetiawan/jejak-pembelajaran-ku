const { Server } = require("socket.io");
const { server } = require("./server");
const { getOrCreateRouter } = require("../lib/router");
const { createProducerTransport, createConsumerTransport } = require("../lib/transport");
const { createProducer } = require("../lib/producer");
const { Router } = require("mediasoup/node/lib/Router");
const { WebRtcTransport } = require("mediasoup/node/lib/WebRtcTransport");
const { Producer } = require("mediasoup/node/lib/Producer");
const { createConsumer } = require("../lib/consumer");
const { EventEmitter } = require("events");
const { onDisconnect } = require("./disconnect");
const serverEmitter = new EventEmitter();
const io = new Server(server);

function runSocket() {
  io.on("connection", socket => {

    /**@type {Router} */
    let router;
    /**@type {WebRtcTransport} */
    let producerTransport;
    /**@type {Set<Producer>} */
    let producers = new Set();
    /**@type {Set<WebRtcTransport>} */
    let consumerTransports = new Set();

    socket.on("join-room", async roomId => {
      socket.join(roomId);
      router = await getOrCreateRouter(roomId, socket);
      console.log("PESERTA ====> ", router.peserta.size);
      socket.emit("routerRtpCapabilities", router.rtpCapabilities);
      socket.on("new-send-transport", remoteSocketId => socket.broadcast.to(roomId).emit("new-send-transport", remoteSocketId));
      socket.on("new-producer", data => {
        socket.broadcast.to(roomId).emit("new-producer", data);
      });
    });

    socket.on("device-loaded", async rtpCapabilities => {
      producerTransport = await createProducerTransport(router, socket);

      producerTransport.observer.on("close", () => {
        serverEmitter.emit("producerTransportClosed", socket.id);
        producerTransport = null;
      });

      socket.on("create-producer", async ({ kind, rtpParameters }) => {
        const producer = await createProducer(producerTransport, kind, rtpParameters, socket);
        producer.observer.on("close", () => producers.delete(producer));
        socket.producers ?
          socket.producers.add(producer) :
          socket.producers = new Set([producer]);
        producers.add(producer);
      });

      socket.on("create-consumer-transport", async remoteSocketId => {
        const consumerTransport = await createConsumerTransport(router, socket);
        consumerTransport.remoteSocketId = remoteSocketId;
        consumerTransports.add(consumerTransport);
      });

      for (const peserta of router.peserta) {
        if (socket.id === peserta.id) continue;
        const consumerTransport = await createConsumerTransport(router, socket);
        consumerTransport.remoteSocketId = peserta.id;
        consumerTransports.add(consumerTransport);

        if (!peserta.producers) continue;
        for (const producer of peserta.producers) {
          await createConsumer(socket, consumerTransport, producer.id, rtpCapabilities);
        }
      }

      socket.on("create-consumer", async ({ producerId, socketId: remoteSocketId }) => {
        for (const consumerTransport of consumerTransports) {
          if (consumerTransport.remoteSocketId === remoteSocketId) {
            await createConsumer(socket, consumerTransport, producerId, rtpCapabilities);
            break;
          }
        }
      });
    });

    serverEmitter.on("producerTransportClosed", socketId => {
      for (const consumerTransport of consumerTransports) {
        if (consumerTransport.remoteSocketId === socketId) consumerTransport.close();
      }
    });

    socket.on("disconnect", () => {
      onDisconnect(router, socket);
    });

  });
}

module.exports = { runSocket }