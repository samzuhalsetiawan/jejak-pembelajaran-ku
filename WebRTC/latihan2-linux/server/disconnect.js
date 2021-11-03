const { mediasoupRouters } = require("./router");
const { mediasoupWorkers } = require("./worker");

// TODO Bersihkan variable

function disconnect(roomID) {
    const temp = mediasoupRouters[roomID].peserta.filter(peserta => peserta.connected);
    mediasoupRouters[roomID].peserta = temp;
    if (mediasoupRouters[roomID].peserta.length < 1) {
        mediasoupRouters[roomID].close();
        mediasoupWorkers.forEach((worker) => {
            if (worker.pid === mediasoupRouters[roomID].workerID) {
                worker.numRouter--;
            }
        });
        delete mediasoupRouters[roomID];
    }
}

module.exports = { disconnect }