function debug(socket) {
  const router = socket.router;
  console.log("<============Router============>");
  console.log("Router ID : ", router.id);
  console.log("Jumlah Peserta : ", router.peserta.size);
  console.log("<============Transport=========>");
  const data = [];
  for (const sock of router.peserta) {
    const temp = {}
    temp.socketId = sock.id;
    temp.producerTransport = sock.producerTransport.id;
    temp.consumerTransports = [];
    sock.consumerTransports.forEach(consumerTransport => {
      temp.consumerTransports.push(consumerTransport.id);
    });
    data.push(temp);
  }
  console.log(JSON.stringify(data, null, 2));
}

function debugPause(socket) {
  console.log("<=================prodCons Debugger==============>");
  const router = socket.router;
  for (const sock of router.peserta) {
    const pTransport = sock.producerTransport;
    const cTransport = [...sock.consumerTransports]
    const producer = pTransport.producers.get("video");
    console.log(`<===========${sock.id}============>`);
    console.log(producer?.paused);
    for (const cons of cTransport) {
      const consumer = cons.consumers.get("video");
      console.log(consumer?.paused)
    }
  }
}

module.exports = { debug, debugPause }