import { WebSocketServer } from "ws";

const PORT = Number(process.env.PORT)

if (!PORT) process.exit();

const wss = new WebSocketServer({port: PORT});

wss.on("connection", (socket) => {
    console.log("client connected");

    socket.on("message", (data) => {
        console.log("server recived: ", data.toString());
    })

    socket.on("close", () => {
        console.log("Disconnected")
    })
})

console.log(`WSS is running on port:${PORT}`)