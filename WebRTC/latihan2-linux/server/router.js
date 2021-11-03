const { config } = require("./config");
const { getWorker } = require("./worker");

let mediasoupRouters = {}

async function createRouter(worker) {
    const router = await worker.createRouter({ mediaCodecs: config.mediasoup.router.mediaCodecs });
    worker.numRouter++;
    return router;
}

async function createRoom(roomID, socket) {
    socket.join(roomID);
    if (mediasoupRouters[roomID]) {
        mediasoupRouters[roomID].peserta.push(socket);
    } else {
        const worker = getWorker();
        const router = await createRouter(worker);
        router.roomID = roomID;
        router.workerID = worker.pid;
        router.peserta = [socket];
        mediasoupRouters[roomID] = router;
    }
}

module.exports = { mediasoupRouters, createRoom, createRouter }