const buttonKirim = document.getElementById("kirim");
const inputTeks = document.getElementById("teksBox");
const videoGrid = document.getElementById("video-grid");
const socket = io();
const config = {'iceServers': [{'urls': 'stun:stun.l.google.com:19302'}]}
const peer = new RTCPeerConnection(config);
let dc = null;

function showChat(chat) {
    const div = document.createElement("div");
    div.innerHTML = chat;
    document.getElementById("chat").appendChild(div);
}
function sendData(data, channel) {
    channel.send(data);
    showChat(data);
}
function addChannelListener(channel) {
    channel.onmessage = msg => showChat(msg.data);
    channel.onopen = e => console.log("Connection Openned");
}
async function getStream() {
    try {
        const stream = await navigator.mediaDevices.getUserMedia({
            video: true,
            audio: true
        });
        const videoTag = document.createElement("video");
        videoTag.srcObject = stream;
        videoTag.addEventListener("loadedmetadata", () => videoTag.play());
        videoTag.muted = true;
        videoGrid.appendChild(videoTag);
        for (const track of stream.getTracks()) {
            peer.addTrack(track);
        }
    } catch (err) {
        console.log(err);
    }
}
getStream();
let inboundStream = null;
peer.ontrack = ev => {
    console.log("track trigerred");
    const videoTag = document.createElement("video");
    videoTag.classList.add("client");
    if (ev.streams && ev.streams[0]) {
        videoTag.srcObject = ev.streams[0];
        console.log("if1");
    } else {
        console.log("else1");
        if (!inboundStream) {
            console.log("if2");
            inboundStream = new MediaStream();
            videoTag.srcObject = inboundStream;
        }
        inboundStream.addTrack(ev.track);
    }
    videoTag.addEventListener("loadedmetadata", () => videoTag.play());
    videoGrid.appendChild(videoTag);
}

peer.onicecandidate = ice => {
    socket.emit("session-desc", {desc : JSON.stringify(peer.localDescription)});
}

socket.on("user-disconnect", () => {
    document.getElementsByClassName("client")[0].remove();
});

if (STATUS == "offering") {
    dc = peer.createDataChannel("channel");
    socket.on("get-answer", answer => {
        peer.setRemoteDescription(answer.answer);
    });
    addChannelListener(dc);
    peer.onnegotiationneeded = async () => {
        try {
            const offer = await peer.createOffer();
            peer.setLocalDescription(offer);
        } catch (err) {
            console.log(err);
        }
    }

} else if (STATUS == "answering") {
    peer.ondatachannel = channel => {
        dc = channel.channel;
        addChannelListener(dc);
    }
    async function getOffer() {
        try {
            let offer = await fetch("/offer");
            offer = await offer.json();
            peer.setRemoteDescription(JSON.parse(offer));
            const answer = await peer.createAnswer();
            peer.setLocalDescription(answer);
        } catch (err) {
            console.log(err);
        }
    }
    getOffer();
    socket.on("new-offer", () => getOffer());
}

buttonKirim.addEventListener("click", function() {
    sendData(inputTeks.value, dc);
});
