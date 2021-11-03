"use strict";

var mediasoupProducers = {};

function createProducer(socket, transport, kind, rtpParameters) {
  var producer;
  return regeneratorRuntime.async(function createProducer$(_context) {
    while (1) {
      switch (_context.prev = _context.next) {
        case 0:
          _context.next = 2;
          return regeneratorRuntime.awrap(transport.produce({
            kind: kind,
            rtpParameters: rtpParameters
          }));

        case 2:
          producer = _context.sent;
          producer.socketID = socket.id;
          producer.transportID = transport.id;
          producer.roomID = transport.roomID;

          if (mediasoupProducers[transport.roomID]) {
            mediasoupProducers[transport.roomID].push(producer);
          } else {
            mediasoupProducers[transport.roomID] = [producer];
          }

          socket.emit("producer-created", producer.id);

        case 8:
        case "end":
          return _context.stop();
      }
    }
  });
}

function onProducer(socket, roomID, producerID, transportID) {
  socket.emit("need-rtpCapabilities");
  socket.on("sended-rtpCapabilities", function (rtpCapabilities) {
    socket.broadcast.to(roomID).emit("new-producer", {
      producerID: producerID,
      rtpCapabilities: rtpCapabilities,
      transportID: transportID
    });
  });
}

module.exports = {
  mediasoupProducers: mediasoupProducers,
  createProducer: createProducer,
  onProducer: onProducer
};