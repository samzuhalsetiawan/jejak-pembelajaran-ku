"use strict";

// const { mediasoupRooms } = require("./router");
var _require = require("./router"),
    mediasoupRouters = _require.mediasoupRouters; // const { mediasoupWorkers } = require("./worker");


var transportDebugData = [];

function addTransportDebug(routerID, socketID, transportID, type) {
  var transportData = {
    routerID: routerID,
    socketID: socketID,
    transportID: transportID,
    type: type
  };
  transportDebugData.push(transportData);
} // function workerDebugger() {
//     const temp = [];
//     mediasoupWorkers.forEach((worker) => {
//         const a = { [worker.pid]: worker.numRouter }
//         temp.push(a);
//     });
//     console.log("<========= Worker Debugger ========>");
//     console.log(temp);
// }
// function routerDebugger() {
//     const temp = [];
//     for (const data in mediasoupRooms) {
//         const a = {
//             roomID: data,
//             workerID: mediasoupRooms[data].workerID,
//             routerID: mediasoupRooms[data].router.id,
//             jumlahPeserta: mediasoupRooms[data].peserta.length
//         }
//         temp.push(a);
//     }
//     console.log("<========= Router Debugger ========>");
//     console.log(temp);
// }


function transportDebugger(config) {
  var defaultConf = {
    json: true
  };

  for (var conf in config) {
    defaultConf[conf] = config[conf];
  }

  console.log("<========= Transport Debugger ========>");
  var temp = {
    room: []
  };

  for (var roomID in mediasoupRouters) {
    var room = {
      roomID: roomID,
      socket: []
    };
    var _iteratorNormalCompletion = true;
    var _didIteratorError = false;
    var _iteratorError = undefined;

    try {
      for (var _iterator = mediasoupRouters[roomID].peserta[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
        var socket = _step.value;
        var sock = {
          socketID: socket.id,
          producerTransport: {
            transportID: null,
            producer: []
          },
          consumerTransport: []
        };
        var _iteratorNormalCompletion2 = true;
        var _didIteratorError2 = false;
        var _iteratorError2 = undefined;

        try {
          for (var _iterator2 = transportDebugData[Symbol.iterator](), _step2; !(_iteratorNormalCompletion2 = (_step2 = _iterator2.next()).done); _iteratorNormalCompletion2 = true) {
            var transportData = _step2.value;

            if (transportData.socketID === socket.id) {
              if (transportData.type === "send") {
                sock.producerTransport.transportID = transportData.transportID;
              } else if (transportData.type === "recv") {
                var consume = {
                  transportID: transportData.transportID,
                  consumer: []
                };
                sock.consumerTransport.push(consume);
              }
            }
          }
        } catch (err) {
          _didIteratorError2 = true;
          _iteratorError2 = err;
        } finally {
          try {
            if (!_iteratorNormalCompletion2 && _iterator2["return"] != null) {
              _iterator2["return"]();
            }
          } finally {
            if (_didIteratorError2) {
              throw _iteratorError2;
            }
          }
        }

        room.socket.push(sock);
      }
    } catch (err) {
      _didIteratorError = true;
      _iteratorError = err;
    } finally {
      try {
        if (!_iteratorNormalCompletion && _iterator["return"] != null) {
          _iterator["return"]();
        }
      } finally {
        if (_didIteratorError) {
          throw _iteratorError;
        }
      }
    }

    temp.room.push(room);
  }

  if (defaultConf.json) {
    console.log(JSON.stringify(temp, null, 2));
    console.log(JSON.stringify(transportDebugData, null, 2));
  } else {
    console.log(temp);
  }
} // module.exports = { workerDebugger, routerDebugger, addTransportDebug, transportDebugger }


module.exports = {
  addTransportDebug: addTransportDebug,
  transportDebugger: transportDebugger
};