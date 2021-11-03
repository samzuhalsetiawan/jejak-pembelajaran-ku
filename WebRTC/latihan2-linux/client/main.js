const mediasoup = require("mediasoup-client");
const socket = io();
const device = new mediasoup.Device();
const roomID = window.location.pathname.substring(1);

const btnDebug = document.getElementById("debug");
const mainVideo = document.getElementsByClassName("main-video");
const remoteVideo = document.getElementsByClassName("remote-video");
const btnCamera = document.getElementById("camera");

let producerTransport;

socket.emit("join-room", roomID);

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
    const userStream = await navigator.mediaDevices.getUserMedia({ video: true });
    const videoTrack = userStream.getVideoTracks()[0];
    await producerTransport.produce({ track: videoTrack });
    addVideo(userStream, mainVideo[0]);
}

function addVideo(stream, container) {
    const video = document.createElement("video");
    video.srcObject = stream;
    video.muted = true;
    video.addEventListener("loadedmetadata", video.play);
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

device.observer.on("newtransport", transport => {
    if (transport.direction === "send") socket.emit("new-producer-transport-created", transport.id);
});

socket.on("need-rtpCapabilities", () => {
    socket.emit("sended-rtpCapabilities", device.rtpCapabilities);
});
socket.on("get-rtpCapabilities-then-create-consumer", ({ producerID, transportID }) => {
    socket.emit("create-consumer", { producerID, rtpCapabilities: device.rtpCapabilities, transportID });
});

socket.on("new-producer-transport", producerTransportID => {
    socket.emit("create-consumer-transport", producerTransportID);
})

//producer transport
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
        socket.on("producer-created", producerID => {
            callback({ id: producerID });
        });
    });
});

// consumer transport
socket.on("create-recv-transport", ({ id, iceParameters, iceCandidates, dtlsParameters }) => {
    const consumerTransport = device.createRecvTransport({ id, iceParameters, iceCandidates, dtlsParameters });
    consumerTransport.on("connect", ({ dtlsParameters }, callback, errback) => {
        socket.emit("connect-consumer-transport", dtlsParameters);
        socket.on("consumer-transport-connected", () => {
            callback();
        });
    });
    socket.on("new-producer", consumerParameter => {
        socket.emit("create-consumer", consumerParameter);
        socket.on("consumer-created", async ({ id, producerId, kind, rtpParameters }) => {
            const consumer = await consumerTransport.consume({ id, producerId, kind, rtpParameters });
            const { track } = consumer;
            const remoteStream = new MediaStream().addTrack(track);
            consumerTransport.on("connectionstatechange", state => {
                if (state === "connected") {
                    addVideo(remoteStream, remoteVideo[0]);
                    socket.emit("resume-consumer");
                    // socket.on("consumer-resumed", () => {
                    // });
                }
            })
        });
    });
});


btnDebug.addEventListener("click", function () {
    socket.emit("debug", roomID);
});