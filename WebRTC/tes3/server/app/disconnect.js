const { Router } = require("mediasoup/node/lib/Router");
const { Socket } = require("socket.io");

/**
 * 
 * @param {Router} router 
 * @param {Socket} socket
 * @param {Event} serverEmitter
 */
function onDisconnect(router, socket) {
  router.peserta.delete(socket);
  if (!router.peserta.size) router.close();
}

module.exports = { onDisconnect }