"use strict";

var _require = require("./router"),
    mediasoupRouters = _require.mediasoupRouters;

var _require2 = require("./worker"),
    mediasoupWorkers = _require2.mediasoupWorkers; // TODO Bersihkan variable


function disconnect(roomID) {
  var temp = mediasoupRouters[roomID].peserta.filter(function (peserta) {
    return peserta.connected;
  });
  mediasoupRouters[roomID].peserta = temp;

  if (mediasoupRouters[roomID].peserta.length < 1) {
    mediasoupRouters[roomID].close();
    mediasoupWorkers.forEach(function (worker) {
      if (worker.pid === mediasoupRouters[roomID].workerID) {
        worker.numRouter--;
      }
    });
    delete mediasoupRouters[roomID];
  }
}

module.exports = {
  disconnect: disconnect
};