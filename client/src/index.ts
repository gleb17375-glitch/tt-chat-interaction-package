import WebSocket from "ws";

const socket = new WebSocket("ws://localhost:8080");

socket.on("open", () => {
    console.log("connected to server");

    socket.send(JSON.stringify({
        test: "message"
    }))
});

socket.on("message", console.log)