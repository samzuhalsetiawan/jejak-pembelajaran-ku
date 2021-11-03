"use strict";

var mediasoupConsumers = {};

function createConsumer(socket, producerId, rtpCapabilities, consumerTransport, transportID) {
  var consumer;
  return regeneratorRuntime.async(function createConsumer$(_context) {
    while (1) {
      switch (_context.prev = _context.next) {
        case 0:
          if (!(consumerTransport.producerTransportID !== transportID)) {
            _context.next = 2;
            break;
          }

          return _context.abrupt("return");

        case 2:
          _context.next = 4;
          return regeneratorRuntime.awrap(consumerTransport.consume({
            producerId: producerId,
            rtpCapabilities: rtpCapabilities,
            paused: true
          }));

        case 4:
          consumer = _context.sent;
          socket.emit("consumer-created", {
            id: consumer.id,
            producerId: consumer.producerId,
            kind: consumer.kind,
            rtpParameters: consumer.rtpParameters,
            consumerTransportID: consumerTransport.id
          });
          socket.on("resume-consumer", function () {
            consumer.resume(); // socket.emit("consumer-resumed");
          });

        case 7:
        case "end":
          return _context.stop();
      }
    }
  });
}

module.exports = {
  mediasoupConsumers: mediasoupConsumers,
  createConsumer: createConsumer
};