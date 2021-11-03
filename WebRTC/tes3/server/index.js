const { runServer } = require("./app/server");
const { createWorker } = require("./lib/worker");
const { runSocket } = require("./app/socket");

async function main() {
  await createWorker();
  await runServer();
  runSocket();
}

main();