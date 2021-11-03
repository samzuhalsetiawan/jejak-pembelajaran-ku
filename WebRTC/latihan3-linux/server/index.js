const { runServer } = require("./app/server");
const { runSocket } = require("./app/socket");
const { createWorker } = require("./lib/worker");

async function main() {
    await createWorker();
    runServer();
    runSocket();
}

main();