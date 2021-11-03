// const { mediasoupRooms } = require("./router");
const { mediasoupRouters } = require("./router");
// const { mediasoupWorkers } = require("./worker");

let transportDebugData = [];

function addTransportDebug(routerID, socketID, transportID, type) {
    const transportData = { routerID, socketID, transportID, type }
    transportDebugData.push(transportData);
}

// function workerDebugger() {
//     const temp = [];
//     mediasoupWorkers.forEach((worker) => {
//         const a = { [worker.pid]: worker.numRouter }
//         temp.push(a);
//     });
//     console.log("<========= Worker Debugger ========>");
//     console.log(temp);
// }

// function routerDebugger() {
//     const temp = [];
//     for (const data in mediasoupRooms) {
//         const a = {
//             roomID: data,
//             workerID: mediasoupRooms[data].workerID,
//             routerID: mediasoupRooms[data].router.id,
//             jumlahPeserta: mediasoupRooms[data].peserta.length
//         }
//         temp.push(a);
//     }
//     console.log("<========= Router Debugger ========>");
//     console.log(temp);
// }

function transportDebugger(config) {
    const defaultConf = { json: true }
    for (const conf in config) {
        defaultConf[conf] = config[conf];
    }
    console.log("<========= Transport Debugger ========>");

    const temp = {
        room: []
    }

    for (const roomID in mediasoupRouters) {
        const room = {
            roomID,
            socket: []
        }
        for (const socket of mediasoupRouters[roomID].peserta) {
            const sock = {
                socketID: socket.id,
                producerTransport: {
                    transportID: null,
                    producer: []
                },
                consumerTransport: []
            }
            for (const transportData of transportDebugData) {
                if (transportData.socketID === socket.id) {
                    if (transportData.type === "send") {
                        sock.producerTransport.transportID = transportData.transportID;
                    } else if (transportData.type === "recv") {
                        const consume = {
                            transportID: transportData.transportID,
                            consumer: []
                        }
                        sock.consumerTransport.push(consume);
                    }
                }
            }
            room.socket.push(sock);
        }
        temp.room.push(room);
    }

    if (defaultConf.json) {
        console.log(JSON.stringify(temp, null, 2));
        console.log(JSON.stringify(transportDebugData, null, 2));
    } else {
        console.log(temp);
    }

}

// module.exports = { workerDebugger, routerDebugger, addTransportDebug, transportDebugger }
module.exports = { addTransportDebug, transportDebugger }