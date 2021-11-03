
const { createRouter } = require("../../lib/router");
const { getWorker } = require("../../lib/worker");

let mediasoupRooms = new Map();

// TODO Pelajari event emitter nodejs untuk socket disconnect

async function joinRoom(socket, roomId) {
    let worker;
    let router;
    socket.join(roomId);
    if (mediasoupRooms.has(roomId)) {
        [worker, router] = mediasoupRooms.get(roomId);
    } else {
        worker = getWorker();
        router = await createRouter(worker);
        worker.routers.add(router);
        mediasoupRooms.set(roomId, [worker, router]);
    }
    socket.worker = worker;
    socket.router = router;
    socket.consumerTransports = new Set();
    router.peserta.add(socket);
}

module.exports = { joinRoom }