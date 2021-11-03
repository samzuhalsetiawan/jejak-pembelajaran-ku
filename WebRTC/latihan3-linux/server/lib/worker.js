const mediasoup = require("mediasoup");
const { config } = require("../etc/config");

const mediasoupWorkers = new Set();

async function createWorker() {
    for (let i = 0; i < config.mediasoup.numWorkers; i++) {
        const worker = await mediasoup.createWorker({
            logLevel: config.mediasoup.worker.logLevel,
            logTags: config.mediasoup.worker.logTags,
            rtcMinPort: config.mediasoup.worker.rtxMinPort,
            rtcMaxPort: config.mediasoup.worker.rtxMaxPort
        });

        worker.on("died", () => {
            console.log(`Worker [${worker.pid}] Died, Exiting in 2s ... `);
            setTimeout(() => {
                mediasoupWorkers.delete(worker);
                process.exit(1);
            }, 2000);
        });

        worker.routers = new Set();
        mediasoupWorkers.add(worker);
    }
}

function getWorker() {
    return [...mediasoupWorkers].reduce((prev, current) => {
        if (prev.routers.size < current.routers.size) return prev;
        return current;
    });
}

module.exports = { createWorker, getWorker }