"use strict";

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

var _require = require("./config"),
    config = _require.config;

var _require2 = require("./debug"),
    addTransportDebug = _require2.addTransportDebug;

var _require3 = require("./producer"),
    onProducer = _require3.onProducer;

var _require4 = require("./consumer"),
    createConsumer = _require4.createConsumer;

var mediasoupProducerTransports = {};
var mediasoupConsumerTransports = {};

function createTransport(router, listenIps) {
  var transport;
  return regeneratorRuntime.async(function createTransport$(_context) {
    while (1) {
      switch (_context.prev = _context.next) {
        case 0:
          _context.next = 2;
          return regeneratorRuntime.awrap(router.createWebRtcTransport({
            listenIps: listenIps,
            enableUdp: true,
            enableTcp: true,
            preferUdp: true,
            initialAvaliableOutgoingBitrate: config.mediasoup.webRtcTransport.initialAvailableOutgoingBitrate
          }));

        case 2:
          transport = _context.sent;

          if (!config.mediasoup.webRtcTransport.maxIncomingBitrate) {
            _context.next = 12;
            break;
          }

          _context.prev = 4;
          _context.next = 7;
          return regeneratorRuntime.awrap(transport.setMaxIncomingBitrate(config.mediasoup.webRtcTransport.maxIncomingBitrate));

        case 7:
          _context.next = 12;
          break;

        case 9:
          _context.prev = 9;
          _context.t0 = _context["catch"](4);
          console.log(_context.t0);

        case 12:
          return _context.abrupt("return", transport);

        case 13:
        case "end":
          return _context.stop();
      }
    }
  }, null, null, [[4, 9]]);
}

function createProducerTransport(socket, router, listenIps) {
  var producerTransport;
  return regeneratorRuntime.async(function createProducerTransport$(_context3) {
    while (1) {
      switch (_context3.prev = _context3.next) {
        case 0:
          _context3.next = 2;
          return regeneratorRuntime.awrap(createTransport(router, listenIps));

        case 2:
          producerTransport = _context3.sent;
          producerTransport.socketID = socket.id;
          producerTransport.roomID = router.roomID;
          socket.emit("create-send-transport", {
            id: producerTransport.id,
            iceParameters: producerTransport.iceParameters,
            iceCandidates: producerTransport.iceCandidates,
            dtlsParameters: producerTransport.dtlsParameters
          });
          socket.on("connect-producer-transport", function _callee(dtlsParameters) {
            return regeneratorRuntime.async(function _callee$(_context2) {
              while (1) {
                switch (_context2.prev = _context2.next) {
                  case 0:
                    _context2.next = 2;
                    return regeneratorRuntime.awrap(producerTransport.connect({
                      dtlsParameters: dtlsParameters
                    }));

                  case 2:
                    socket.emit("producer-transport-connected");

                  case 3:
                  case "end":
                    return _context2.stop();
                }
              }
            });
          });
          producerTransport.observer.on("newproducer", function (producer) {
            onProducer(socket, router.roomID, producer.id, producerTransport.id);
          });

          if (mediasoupProducerTransports[router.roomID]) {
            mediasoupProducerTransports[router.roomID].push(producerTransport);
          } else {
            mediasoupProducerTransports[router.roomID] = [producerTransport];
          }

          addTransportDebug(router.id, socket.id, producerTransport.id, "send");
          return _context3.abrupt("return", producerTransport);

        case 11:
        case "end":
          return _context3.stop();
      }
    }
  });
}

function createConsumerTransport(socket, router, listenIps, producerTransportID) {
  var _iteratorNormalCompletion, _didIteratorError, _iteratorError, _iterator, _step, transport, consumerTransport;

  return regeneratorRuntime.async(function createConsumerTransport$(_context6) {
    while (1) {
      switch (_context6.prev = _context6.next) {
        case 0:
          _context6.prev = 0;
          _iteratorNormalCompletion = true;
          _didIteratorError = false;
          _iteratorError = undefined;
          _context6.prev = 4;
          _iterator = mediasoupConsumerTransports[router.roomID][socket.id][Symbol.iterator]();

        case 6:
          if (_iteratorNormalCompletion = (_step = _iterator.next()).done) {
            _context6.next = 13;
            break;
          }

          transport = _step.value;

          if (!(transport.producerTransportID === producerTransportID)) {
            _context6.next = 10;
            break;
          }

          return _context6.abrupt("return");

        case 10:
          _iteratorNormalCompletion = true;
          _context6.next = 6;
          break;

        case 13:
          _context6.next = 19;
          break;

        case 15:
          _context6.prev = 15;
          _context6.t0 = _context6["catch"](4);
          _didIteratorError = true;
          _iteratorError = _context6.t0;

        case 19:
          _context6.prev = 19;
          _context6.prev = 20;

          if (!_iteratorNormalCompletion && _iterator["return"] != null) {
            _iterator["return"]();
          }

        case 22:
          _context6.prev = 22;

          if (!_didIteratorError) {
            _context6.next = 25;
            break;
          }

          throw _iteratorError;

        case 25:
          return _context6.finish(22);

        case 26:
          return _context6.finish(19);

        case 27:
          _context6.next = 31;
          break;

        case 29:
          _context6.prev = 29;
          _context6.t1 = _context6["catch"](0);

        case 31:
          _context6.next = 33;
          return regeneratorRuntime.awrap(createTransport(router, listenIps));

        case 33:
          consumerTransport = _context6.sent;
          consumerTransport.socketID = socket.id;
          consumerTransport.roomID = router.roomID;
          consumerTransport.producerTransportID = producerTransportID;
          socket.emit("create-recv-transport", {
            id: consumerTransport.id,
            iceParameters: consumerTransport.iceParameters,
            iceCandidates: consumerTransport.iceCandidates,
            dtlsParameters: consumerTransport.dtlsParameters
          });
          socket.on("connect-consumer-transport", function _callee2(dtlsParameters) {
            return regeneratorRuntime.async(function _callee2$(_context4) {
              while (1) {
                switch (_context4.prev = _context4.next) {
                  case 0:
                    _context4.next = 2;
                    return regeneratorRuntime.awrap(consumerTransport.connect({
                      dtlsParameters: dtlsParameters
                    }));

                  case 2:
                    socket.emit("consumer-transport-connected");

                  case 3:
                  case "end":
                    return _context4.stop();
                }
              }
            });
          });
          socket.on("create-consumer", function _callee3(_ref) {
            var producerID, rtpCapabilities, transportID;
            return regeneratorRuntime.async(function _callee3$(_context5) {
              while (1) {
                switch (_context5.prev = _context5.next) {
                  case 0:
                    producerID = _ref.producerID, rtpCapabilities = _ref.rtpCapabilities, transportID = _ref.transportID;
                    _context5.next = 3;
                    return regeneratorRuntime.awrap(createConsumer(socket, producerID, rtpCapabilities, consumerTransport, transportID));

                  case 3:
                  case "end":
                    return _context5.stop();
                }
              }
            });
          });

          if (mediasoupConsumerTransports[router.roomID]) {
            if (mediasoupConsumerTransports[router.roomID][socket.id]) {
              mediasoupConsumerTransports[router.roomID][socket.id].push(consumerTransport);
            } else {
              mediasoupConsumerTransports[router.roomID][socket.id] = [consumerTransport];
            }
          } else {
            mediasoupConsumerTransports[router.roomID] = _defineProperty({}, socket.id, [consumerTransport]);
          }

          addTransportDebug(router.id, socket.id, consumerTransport.id, "recv");

        case 42:
        case "end":
          return _context6.stop();
      }
    }
  }, null, null, [[0, 29], [4, 15, 19, 27], [20,, 22, 26]]);
}

module.exports = {
  createProducerTransport: createProducerTransport,
  createConsumerTransport: createConsumerTransport,
  createTransport: createTransport,
  mediasoupConsumerTransports: mediasoupConsumerTransports,
  mediasoupProducerTransports: mediasoupProducerTransports
};