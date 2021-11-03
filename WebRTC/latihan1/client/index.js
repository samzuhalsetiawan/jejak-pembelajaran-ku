const mediasoup = require("mediasoup-client");

const socket = io();
const device = new mediasoup.Device();

let userStream;
let remoteStream;
let producerTransport;

// Selector
const btnOpenCam = document.getElementsByClassName("open-cam");
const btnCloseCam = document.getElementsByClassName("close-cam");
const divUserVideo = document.getElementsByClassName("main-video");
const divRemoteVideo = document.getElementsByClassName("remote-video");

const roomID = window.location.pathname.substring(1);

//listener
btnOpenCam[0].addEventListener("click", openCam);

// socket
socket.emit("join-room", roomID);

// TODO Consume Media from Server
// Consume
socket.on("consumer-transport-created", ({ id, iceParameters, iceCandidates, dtlsParameters }) => {
    console.log("consumer-transport-created");
    consumerTransport = device.createRecvTransport({ id, iceParameters, iceCandidates, dtlsParameters });
    consumerTransport.on("connect", ({ dtlsParameters }, callback, errback) => {
        console.log("consumer connect event");
        socket.emit("connect-consumer-transport", dtlsParameters);
        socket.on("consumer-transport-connected", () => {
            callback();
            console.log("consumer callback called");
        });
    });
    socket.emit("consume-needed", device.rtpCapabilities);
    socket.on("consumer-created", async ({ id, producerId, kind, rtpParameters }) => {
        const consumer = await consumerTransport.consume({ id, producerId, kind, rtpParameters });
        remoteStream = new MediaStream();
        remoteStream.addTrack(consumer.track);
        consumerTransport.on("connectionstatechange", state => {
            console.log("state consume ===> ", state);
            switch (state) {
                case "connected":
                    console.log("RemoteStream: ===> ", remoteStream);
                    divRemoteVideo[0].appendChild(createVideo(remoteStream));
                    socket.emit("resume-please");
                    break;
                case "failed":
                    divRemoteVideo[0].innerHTML = "Failed to get Remote Stream";
                    break;
                default:
                    console.log(state);
                    break;
            }
        });
    });
});

socket.on("producer-transport-created", ({ id, iceParameters, iceCandidates, dtlsParameters }) => {
    console.log("ice Sended from server");
    producerTransport = device.createSendTransport({ id, iceParameters, iceCandidates, dtlsParameters });
    producerTransport.on("connect", ({ dtlsParameters }, callback, errback) => {
        console.log("producer-transport-created");
        socket.emit("connect-producer-transport", dtlsParameters);
        socket.on("producer-transport-connected", () => {
            console.log("producer-transport-connected");
            callback();
        });
    });
    producerTransport.on("produce", ({ kind, rtpParameters }, callback, errback) => {
        try {
            socket.emit("create-producer", { kind, rtpParameters });
            socket.on("producer-created", producerID => {
                callback({ id: producerID });
                console.log("callback dijalankan:", producerID);
            });
        } catch (err) {
            console.log("error di producerTransport on produce");
            console.log(err);
        }
    });
});

socket.on("router-rtpCapabilities", async routerRtpCapabilities => {
    await device.load({ routerRtpCapabilities });
});

socket.on("debugging", data => {
    console.log(JSON.parse(data));
});

// function
function createVideo(stream) {
    const video = document.createElement("video");
    video.srcObject = stream;
    video.muted = true;
    video.addEventListener("loadedmetadata", video.play);
    return video;
}
async function openCam(ev) {
    userStream = await navigator.mediaDevices.getUserMedia({ video: true });
    divUserVideo[0].appendChild(createVideo(userStream));
    const track = userStream.getVideoTracks()[0];
    await producerTransport.produce({ track });
}