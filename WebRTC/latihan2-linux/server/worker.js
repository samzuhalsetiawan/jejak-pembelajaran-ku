const mediasoup = require("mediasoup");
const { config } = require("./config");

let mediasoupWorkers = [];

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
                mediasoupWorkers = mediasoupWorkers.filter((msworker) => msworker.pid !== worker.pid);
                process.exit(1);
            }, 2000);
        });

        worker.numRouter = 0;
        mediasoupWorkers.push(worker);
    }
}

function getWorker() {
    return mediasoupWorkers.reduce((prev, current) => {
        if (prev.numRouter < current.numRouter) return prev;
        return current;
    });
}

module.exports = { createWorker, getWorker, mediasoupWorkers }