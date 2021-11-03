const socket = io();
const mediasoup = require("mediasoup-client");
const device = new mediasoup.Device();

const roomId = window.location.pathname.substring(1);

let producerTransport;

let remoteStreams = new Map();

const _shareVideo = document.querySelector("#share-video");
const _userVideo = document.querySelector(".user-video");
const _remote = document.querySelector(".remote");
const _remoteVideo = document.getElementsByTagName("video");
const _unmute = document.querySelector("#unmute");

_shareVideo.addEventListener("click", async function () {
  const userStream = await navigator.mediaDevices.getUserMedia({ video: { height: 720, width: 1280 }, audio: true });
  const vTrack = userStream.getVideoTracks()[0];
  const aTrack = userStream.getAudioTracks()[0];
  await producerTransport.produce({ track: vTrack });
  await producerTransport.produce({ track: aTrack });
  insertVideo(userStream, _userVideo);
});

_unmute.addEventListener("click", function () {
  for (let i = 0; i < _remoteVideo.length; i++) {
    const video = _remoteVideo[i];
    video.muted = false;
  }
});

function insertVideo(stream, container) {
  const video = document.createElement("video");
  video.srcObject = stream;
  video.muted = true;
  video.setAttribute("width", "400");
  video.onloadedmetadata = () => {
    video.play();
  }
  container.appendChild(video);
}

socket.on("connect", () => {
  socket.emit("join-room", roomId);
});

socket.on("routerRtpCapabilities", async routerRtpCapabilities => {
  try {
    await device.load({ routerRtpCapabilities });
    socket.emit("device-loaded", device.rtpCapabilities);
  } catch (error) {
    console.log(error);
    alert("Terjadi masalah saat load device, Mohon restart kembali browser anda");
  }
});

device.observer.on("newtransport", transport => {
  if (transport.direction === "send") socket.emit("new-send-transport", socket.id);
});

socket.on("new-send-transport", remoteSocketId => socket.emit("create-consumer-transport", remoteSocketId));

socket.on("new-producer", data => socket.emit("create-consumer", data));

socket.on("create-send-transport", ({ id, iceCandidates, iceParameters, dtlsParameters }) => {
  producerTransport = device.createSendTransport({ id, iceParameters, iceCandidates, dtlsParameters });

  producerTransport.on("connect", ({ dtlsParameters }, callback, errback) => {
    socket.emit("connect-send-transport", dtlsParameters);
    socket.on("send-tansport-connected", () => {
      callback();
    });
  });

  producerTransport.on("produce", ({ kind, rtpParameters }, callback, errback) => {
    socket.emit("create-producer", { kind, rtpParameters });
    socket.on("producer-created", producerId => {
      callback({ id: producerId });
    });
  });

  producerTransport.observer.on("newproducer", producer => {
    socket.emit("new-producer", { producerId: producer.id, socketId: socket.id });
  });

});

socket.on("create-recv-transport", ({ id, iceCandidates, iceParameters, dtlsParameters }) => {
  const consumerTransport = device.createRecvTransport({ id, iceCandidates, iceParameters, dtlsParameters });

  consumerTransport.on("connect", ({ dtlsParameters }, callback, errback) => {
    socket.emit("connect-recv-transport", dtlsParameters);
    socket.on("recv-transport-connected", () => {
      callback();
    });
  });

  socket.on("consumer-created", async ({ id, producerId, kind, rtpParameters, transportId }) => {
    if (consumerTransport.id === transportId) {
      const consumer = await consumerTransport.consume({ id, producerId, kind, rtpParameters });
      socket.emit("resume-consumer");
      socket.on("consumer-resumed", () => {
        function displayRemoteVideo() {
          const { track } = consumer;
          if (remoteStreams.has(consumerTransport.id)) {
            const remoteStream = remoteStreams.get(consumerTransport.id);
            remoteStream.addTrack(track);
          } else {
            const remoteStream = new MediaStream();
            remoteStream.addTrack(track);
            insertVideo(remoteStream, _remote);
            remoteStreams.set(consumerTransport.id, remoteStream);
          }
        }
        if (consumerTransport.connectionState === "connected") {
          displayRemoteVideo();
        } else {
          consumerTransport.on("connectionstatechange", state => {
            if (state === "connected") {
              displayRemoteVideo()
            } else if (state === "failed") {
              console.log("WebRTC Connection Failed !");
            } else {
              console.log("WebRTC State ===> ", state);
            }
          });
        }
      });
    }
  })

});