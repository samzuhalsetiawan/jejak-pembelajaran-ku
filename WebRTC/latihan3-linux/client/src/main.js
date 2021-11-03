const mediasoup = require("mediasoup-client");
const device = new mediasoup.Device();
const socket = io();

const roomId = window.location.pathname.substring(1);

const btnDebug = document.getElementById("debug");
const mainVideo = document.getElementsByClassName("main-video");
const remoteVideo = document.getElementsByClassName("remote-video");
const btnCamera = document.getElementById("camera");

let producerTransport;

socket.on("connect", () => {
    socket.emit("join-room", roomId);
});

btnCamera.addEventListener("click", async function () {
    if (btnCamera.classList.contains("open")) {
        await openCam();
        btnCamera.classList.remove("open");
        btnCamera.innerHTML = "Close Camera"
    } else {
        closeCam();
        btnCamera.classList.add("open");
        btnCamera.innerHTML = "Open Camera"
    }
});

async function openCam() {
    const userStream = await navigator.mediaDevices.getUserMedia({ video: { width: 1280, height: 720 } });
    const videoTrack = userStream.getVideoTracks()[0];
    await producerTransport.produce({ track: videoTrack });
    addVideo(userStream, mainVideo[0]);
}

function addVideo(stream, container) {
    const video = document.createElement("video");
    video.srcObject = stream;
    video.muted = true;
    video.setAttribute("width", "400");
    video.onloadedmetadata = () => {
        video.play();
    }
    container.appendChild(video);
}

socket.on("router-rtpCapabilities", async routerRtpCapabilities => {
    try {
        await device.load({ routerRtpCapabilities });
    } catch (err) {
        console.error(err);
    }
    if (device.loaded) {
        socket.emit("client-device-loaded"); //? you need send IP here
    } else {
        alert("Device gagal diload, mohon reload kembali browser anda");
    }
});

socket.on("get-ip", () => {
    // ? Send IP Here
});

device.observer.on("newtransport", transport => {
    if (transport.direction === "send") socket.emit("new-producer-transport-created");
});

socket.on("create-send-transport", ({ id, iceParameters, iceCandidates, dtlsParameters }) => {
    producerTransport = device.createSendTransport({ id, iceParameters, iceCandidates, dtlsParameters });
    producerTransport.on("connect", ({ dtlsParameters }, callback, errback) => {
        socket.emit("connect-producer-transport", dtlsParameters);
        socket.on("producer-transport-connected", () => {
            callback();
        });
    });
    producerTransport.on("produce", ({ kind, rtpParameters }, callback, errback) => {
        socket.emit("create-producer", { kind, rtpParameters });
        socket.on("producer-created", producerId => {
            callback({ id: producerId });
        });
    });
});

socket.on("new-producer-transport", () => {
    socket.emit("create-consumer-transport");
})

socket.on("create-recv-transport", ({ id, iceParameters, iceCandidates, dtlsParameters }) => {
    const consumerTransport = device.createRecvTransport({ id, iceParameters, iceCandidates, dtlsParameters });
    consumerTransport.on("connect", ({ dtlsParameters }, callback, errback) => {
        socket.emit("connect-consumer-transport", dtlsParameters);
        socket.on("consumer-transport-connected", () => {
            callback();
        });
    });
    socket.on("new-producer-created", ({ producerId, kind }) => {
        socket.emit("create-consumer", { producerId, rtpCapabilities: device.rtpCapabilities, kind });
        socket.on("consumer-created", async ({ id, producerId, kind, rtpParameters }) => {
            const consumer = await consumerTransport.consume({ id, producerId, kind, rtpParameters });
            socket.emit("resume-consumer");
            socket.on("consumer-resumed", () => {
                const { track } = consumer;
                const remoteStream = new MediaStream();
                remoteStream.addTrack(track);
                addVideo(remoteStream, remoteVideo[0]);
            });
        });
    });
});


document.querySelector("#debug").addEventListener("click", function () {
    socket.emit("debug");
});