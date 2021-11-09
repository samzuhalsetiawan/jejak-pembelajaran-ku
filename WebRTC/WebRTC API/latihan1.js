const peer = new RTCPeerConnection();
const dataChannel = peer.createDataChannel("channel");
dataChannel.onmessage = ev => console.log("Pesan Baru: ",ev.data);
dataChannel.onopen = () => console.log("Peer Connected!!!");
peer.onicecandidate = () => console.log(JSON.stringify(peer.localDescription));
peer.createOffer().then(offer => peer.setLocalDescription(offer));


///////////////////////////////////////////////////
const peerB = new RTCPeerConnection();
peerB.onicecandidate = () => console.log(JSON.stringify(peerB.localDescription));
let dataChannelB;
peerB.ondatachannel = e => {
	dataChannelB = e.channel;
	dataChannelB.onmessage = e => console.log("Pesan Baru: ", e.data);
	dataChannelB.onopen = () => console.log("Peer Connected!!!");
}
peerB.setRemoteDescription(offer);
peerB.createAnswer().then(answer => peerB.setLocalDescription(answer));

////////////////////////////////////////////////////////
dataChannel.send("Hello World");