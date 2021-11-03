"use strict";

var _require = require("./config"),
    config = _require.config;

var _require2 = require("./router"),
    mediasoupRouters = _require2.mediasoupRouters,
    createRoom = _require2.createRoom;

var _require3 = require("./transport"),
    createProducerTransport = _require3.createProducerTransport,
    createConsumerTransport = _require3.createConsumerTransport,
    mediasoupProducerTransports = _require3.mediasoupProducerTransports;

var _require4 = require("./producer"),
    createProducer = _require4.createProducer,
    mediasoupProducers = _require4.mediasoupProducers;

var _require5 = require("./disconnect"),
    disconnect = _require5.disconnect;

var _require6 = require("./debug"),
    transportDebugger = _require6.transportDebugger;

var _require7 = require("./consumer"),
    createConsumer = _require7.createConsumer;

function websocket(io) {
  return regeneratorRuntime.async(function websocket$(_context6) {
    while (1) {
      switch (_context6.prev = _context6.next) {
        case 0:
          io.on("connection", function (socket) {
            socket.on("join-room", function _callee5(roomID) {
              return regeneratorRuntime.async(function _callee5$(_context5) {
                while (1) {
                  switch (_context5.prev = _context5.next) {
                    case 0:
                      _context5.next = 2;
                      return regeneratorRuntime.awrap(createRoom(roomID, socket));

                    case 2:
                      socket.emit("router-rtpCapabilities", mediasoupRouters[roomID].rtpCapabilities);
                      socket.on("client-device-loaded", function _callee4() {
                        var producerTransport;
                        return regeneratorRuntime.async(function _callee4$(_context4) {
                          while (1) {
                            switch (_context4.prev = _context4.next) {
                              case 0:
                                _context4.next = 2;
                                return regeneratorRuntime.awrap(createProducerTransport(socket, mediasoupRouters[roomID], config.mediasoup.webRtcTransport.listenIps));

                              case 2:
                                producerTransport = _context4.sent;
                                socket.on("create-producer", function _callee(_ref) {
                                  var kind, rtpParameters;
                                  return regeneratorRuntime.async(function _callee$(_context) {
                                    while (1) {
                                      switch (_context.prev = _context.next) {
                                        case 0:
                                          kind = _ref.kind, rtpParameters = _ref.rtpParameters;
                                          _context.next = 3;
                                          return regeneratorRuntime.awrap(createProducer(socket, producerTransport, kind, rtpParameters));

                                        case 3:
                                        case "end":
                                          return _context.stop();
                                      }
                                    }
                                  });
                                });
                                mediasoupProducerTransports[roomID].forEach(function _callee2(transport) {
                                  return regeneratorRuntime.async(function _callee2$(_context2) {
                                    while (1) {
                                      switch (_context2.prev = _context2.next) {
                                        case 0:
                                          if (!(transport.socketID !== socket.id)) {
                                            _context2.next = 3;
                                            break;
                                          }

                                          _context2.next = 3;
                                          return regeneratorRuntime.awrap(createConsumerTransport(socket, mediasoupRouters[roomID], config.mediasoup.webRtcTransport.listenIps, transport.id));

                                        case 3:
                                        case "end":
                                          return _context2.stop();
                                      }
                                    }
                                  });
                                });

                                if (mediasoupProducers[roomID]) {
                                  mediasoupProducers[roomID].forEach(function (producer) {
                                    socket.emit("get-rtpCapabilities-then-create-consumer", {
                                      producerID: producer.id,
                                      transportID: producer.transportID
                                    });
                                  });
                                }

                                socket.on("create-consumer-transport", function _callee3(producerTransportID) {
                                  return regeneratorRuntime.async(function _callee3$(_context3) {
                                    while (1) {
                                      switch (_context3.prev = _context3.next) {
                                        case 0:
                                          _context3.next = 2;
                                          return regeneratorRuntime.awrap(createConsumerTransport(socket, mediasoupRouters[roomID], config.mediasoup.webRtcTransport.listenIps, producerTransportID));

                                        case 2:
                                        case "end":
                                          return _context3.stop();
                                      }
                                    }
                                  });
                                });

                              case 7:
                              case "end":
                                return _context4.stop();
                            }
                          }
                        });
                      });
                      socket.on("new-producer-transport-created", function (producerTransportID) {
                        socket.broadcast.to(roomID).emit("new-producer-transport", producerTransportID);
                      });
                      socket.on("disconnect", function () {
                        disconnect(roomID);
                      });
                      socket.on("debug", function (roomID) {
                        // workerDebugger();
                        // routerDebugger();
                        transportDebugger({
                          json: true
                        });
                      });

                    case 7:
                    case "end":
                      return _context5.stop();
                  }
                }
              });
            });
          });

        case 1:
        case "end":
          return _context6.stop();
      }
    }
  });
}

module.exports = {
  websocket: websocket
};