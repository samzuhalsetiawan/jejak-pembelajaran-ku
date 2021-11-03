const mediasoup = require("mediasoup");
const { config } = require("../config");

/**@type {Set.<mediasoup.types.Worker>} */
let mediasoupWorkers = new Set();

async function createWorker() {
  for (let i = 0; i < config.mediasoup.numWorkers; i++) {
    const worker = await mediasoup.createWorker({
      logLevel: config.mediasoup.worker.logLevel,
      logTags: config.mediasoup.worker.logTags,
      rtcMinPort: config.mediasoup.worker.rtxMinPort,
      rtcMaxPort: config.mediasoup.worker.rtxMaxPort
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