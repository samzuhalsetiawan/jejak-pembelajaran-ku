const { Router } = require("mediasoup/node/lib/Router");
const { Socket } = require("socket.io");
const { config } = require("../config");

/**
 * 
 * @param {Router} router 
 */
async function createTransport(router) {
  const transport = await router.createWebRtcTransport({
    listenIps: config.mediasoup.webRtcTransport.listenIps,
    enableTcp: true,
    enableUdp: true,
    preferUdp: true,
    initialAvailableOutgoingBitrate:
      config.mediasoup.webRtcTransport.initialAvailableOutgoingBitrate
  });

  if (config.mediasoup.webRtcTransport.maxIncomingBitrate) {
    transport.setMaxIncomingBitrate(config.mediasoup.webRtcTransport.maxIncomingBitrate);
  }

  return transport;
}

/**
 * 
 * @param {Router} router 
 * @param {Socket} socket 
 */
async function createProducerTransport(router, socket) {
  const producerTransport = await createTransport(router);
  socket.emit("create-send-transport", {
    id: producerTransport.id,
    iceCandidates: producerTransport.iceCandidates,
    iceParameters: producerTransport.iceParameters,
    dtlsParameters: producerTransport.dtlsParameters
  });
  socket.on("connect-send-transport", async dtlsParameters => {
    await producerTransport.connect({ dtlsParameters });
    socket.emit("send-tansport-connected");
    console.log("SEND DTLS STATE CONNECT ==========> ", producerTransport.dtlsState);
    producerTransport.on("dtlsstatechange", state => {
      console.log("SEND DTLS STATE ==========> ", state);
    });
  });
  return producerTransport;
}

/**
 * 
 * @param {Router} router 
 * @param {Socket} socket 
 */
async function createConsumerTransport(router, socket) {
  const consumerTransport = await createTransport(router);
  socket.emit("create-recv-transport", {
    id: consumerTransport.id,
    iceCandidates: consumerTransport.iceCandidates,
    iceParameters: consumerTransport.iceParameters,
    dtlsParameters: consumerTransport.dtlsParameters
  });
  socket.on("connect-recv-transport", async dtlsParameters => {
    consumerTransport.connect({ dtlsParameters });
    socket.emit("recv-transport-connected");
    console.log("RECV DTLS STATE CONNECT ==========> ", consumerTransport.dtlsState);
    consumerTransport.on("dtlsstatechange", state => {
      console.log("RECV DTLS STATE ==========> ", state);
    });
  });
  return consumerTransport;
}

module.exports = { createProducerTransport, createConsumerTransport }