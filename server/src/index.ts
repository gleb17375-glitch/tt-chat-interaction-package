import { WebSocketServer } from "ws";

const SERVER_PORT = Number(process.env.SERVER_PORT)

if (!SERVER_PORT) process.exit();

const wss = new WebSocketServer({port: SERVER_PORT});

wss.on("connection", (socket) => {
    console.log("client connected");

    socket.on("message", (data) => {
        // console.log("server recived: ", data.toString());

        wss.clients.forEach((client) => {
        if (client !== socket && client.readyState === WebSocket.OPEN) {
            client.send(data)
        }
    })
    })

    socket.on("close", () => {
        console.log("Disconnected")
    })
})

console.log(`WSS is running on port:${SERVER_PORT}`)