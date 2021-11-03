const { Router } = require("mediasoup/node/lib/Router");
const { Socket } = require("socket.io");
const { config } = require("../config");
const { getWorker } = require("./worker");

/**@type {Map.<String, Router>} */
let mediasoupRouters = new Map();

/**
 * 
 * @param {String} roomId 
 * @param {Socket} socket 
 */
async function getOrCreateRouter(roomId, socket) {
  if (mediasoupRouters.has(roomId)) {
    const router = mediasoupRouters.get(roomId);
    router.peserta.add(socket);
    return router;
  } else {
    const worker = getWorker();
    const router = await worker.createRouter({
      mediaCodecs: config.mediasoup.router.mediaCodecs
    });
    router.peserta = new Set([socket]);
    worker.routers.add(router);
    mediasoupRouters.set(roomId, router);
    return router;
  }
}

module.exports = { getOrCreateRouter }