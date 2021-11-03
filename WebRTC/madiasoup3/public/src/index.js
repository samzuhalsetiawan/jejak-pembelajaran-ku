const mediasoup = require("mediasoup-client");

const socket = io();
socket.emit("joinRoom", { roomID: ROOM_ID });

const btnShareCam = document.getElementById("share-cam");
const btnConsume = document.getElementById("consume");
const textPublish = document.getElementById("text-publish");
const container = document.querySelector(".container");
const textConsume = document.getElementById("text-consume");
let device;
let stream;
let remoteStream;
let producer;

function publish(e) {
    socket.emit("createProducerTransport", { forceTcp: false, rtpCapabilities: device.rtpCapabilities });
}

function startConsume(e) {
    socket.emit("createConsumeTransport", { forceTcp: false });
}

const loadDevice = async (routerRtpCapabilities) => {
    try {
        device = new mediasoup.Device();
    } catch (err) {
        if (err.name == "UnsupportedError") {
            console.log("Browser not Supported");
        }
    }
    await device.load({ routerRtpCapabilities });
}

socket.on("routerCapabilities", rtpCapabilities => {
    loadDevice(rtpCapabilities);
});

socket.on("producerTansportCreated", async (params) => {
    const transport = device.createSendTransport(params);
    transport.on("connect", async ({ dtlsParameters }, callback, errback) => {
        socket.emit("connectProducerTransport", dtlsParameters);
        socket.on("producerConnected", () => {
            callback();
        });
    });
    // begin transport producer
    transport.on("produce", async ({ kind, rtpParameters }, callback, errback) => {
        socket.emit("produce", { transportId: transport.id, kind, rtpParameters });
        socket.on("produced", producerID => {
            callback(producerID);
        });
    });
    // end transport producer
    transport.on("connectionstatechange", state => {
        switch (state) {
            case 'connecting':
                textPublish.innerHTML = 'publishing...';
                break;
            case 'connected':
                showLocalVideo(stream);
                textPublish.innerHTML = 'published';
                break;
            case 'failed':
                transport.close();
                textPublish.innerHTML = 'failed';
                break;
        }
    });
    try {
        stream = await navigator.mediaDevices.getUserMedia({ video: true });
        const track = stream.getVideoTracks()[0];
        producer = await transport.produce({ track });
    } catch (err) {
        console.error(err);
        textPublish.innerHTML = 'failed!';
    }
});

socket.on("consumerTransportCreated", params => {
    const transport = device.createRecvTransport(params);
    transport.on("connect", ({ dtlsParameters }, callback, errback) => {
        socket.emit("connectConsumerTransport", { transportID: transport.id, dtlsParameters });
        socket.on("consumerTransportConnected", () => {
            callback();
        });
    });
    transport.on("connectionstatechange", state => {
        switch (state) {
            case 'connecting':
                textConsume.innerHTML = 'consuming...';
                break;
            case 'connected':
                showLocalVideo(remoteStream);
                socket.emit("resume");
                textConsume.innerHTML = 'published';
                break;
            case 'failed':
                transport.close();
                textConsume.innerHTML = 'failed';
                break;
        }
    });

    consuming(transport);

});

const consuming = async (transport) => {
    const { rtpCapabilities } = device;
    socket.emit("consume", { rtpCapabilities });
    socket.on("consumed", async (res) => {
        const {
            producerId,
            id,
            kind,
            rtpParameters,
            type,
            producerPaused
        } = res;
        let codecOptions = {};
        const consumer = await transport.consume({
            producerId,
            id,
            kind,
            rtpParameters,
            codecOptions
        });
        const emptyStream = new MediaStream();
        emptyStream.addTrack(consumer.track);
        remoteStream = emptyStream;
    });
}

btnShareCam.addEventListener("click", publish);
btnConsume.addEventListener("click", startConsume);

function showLocalVideo(userStream) {
    const video = document.createElement("video");
    video.srcObject = userStream;
    video.addEventListener("loadedmetadata", video.play());
    video.muted = true;
    container.appendChild(video);
}