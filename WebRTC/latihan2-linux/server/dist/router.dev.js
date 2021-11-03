"use strict";

var _require = require("./config"),
    config = _require.config;

var _require2 = require("./worker"),
    getWorker = _require2.getWorker;

var mediasoupRouters = {};

function createRouter(worker) {
  var router;
  return regeneratorRuntime.async(function createRouter$(_context) {
    while (1) {
      switch (_context.prev = _context.next) {
        case 0:
          _context.next = 2;
          return regeneratorRuntime.awrap(worker.createRouter({
            mediaCodecs: config.mediasoup.router.mediaCodecs
          }));

        case 2:
          router = _context.sent;
          worker.numRouter++;
          return _context.abrupt("return", router);

        case 5:
        case "end":
          return _context.stop();
      }
    }
  });
}

function createRoom(roomID, socket) {
  var worker, router;
  return regeneratorRuntime.async(function createRoom$(_context2) {
    while (1) {
      switch (_context2.prev = _context2.next) {
        case 0:
          socket.join(roomID);

          if (!mediasoupRouters[roomID]) {
            _context2.next = 5;
            break;
          }

          mediasoupRouters[roomID].peserta.push(socket);
          _context2.next = 13;
          break;

        case 5:
          worker = getWorker();
          _context2.next = 8;
          return regeneratorRuntime.awrap(createRouter(worker));

        case 8:
          router = _context2.sent;
          router.roomID = roomID;
          router.workerID = worker.pid;
          router.peserta = [socket];
          mediasoupRouters[roomID] = router;

        case 13:
        case "end":
          return _context2.stop();
      }
    }
  });
}

module.exports = {
  mediasoupRouters: mediasoupRouters,
  createRoom: createRoom,
  createRouter: createRouter
};