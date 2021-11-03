const { config } = require("../etc/config");

async function createRouter(worker) {
    const router = await worker.createRouter({ mediaCodecs: config.mediasoup.router.mediaCodecs });
    router.peserta = new Set();
    worker.numRouter++;
    return router;
}

module.exports = { createRouter }