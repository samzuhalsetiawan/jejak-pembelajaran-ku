// const ws = new WebSocket("ws://belajar-websocket.herokuapp.com/");
const ws = new WebSocket("wss://belajar-websocket.herokuapp.com:8080");

ws.onmessage = ({data}) => {
    const chats = JSON.parse(data)
    document.getElementById("chat").innerHTML = "";
    chats.forEach(chat => {
        const div = document.createElement("div");
        div.innerHTML = chat;
        document.getElementById("chat").appendChild(div);
    });
    console.log(chats);
}

function myButton() {
    ws.send(document.getElementById("teks").value);
}