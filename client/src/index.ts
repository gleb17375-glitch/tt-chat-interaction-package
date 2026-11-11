import WebSocket from "ws";
import { MOCK } from "./mock.js";

const socket = new WebSocket("ws://localhost:8080");

socket.on("open", () => {
    console.log("connected to server");

    MOCK.forEach(message => {
        socket.send(JSON.stringify(message))
    })

    // socket.send(JSON.stringify({
    //     test: "message"
    // }))
    
    // socket.send(JSON.stringify({
    //     type: "like"
    // }))
});

socket.on("message", (data) => {
    const text = data.toString();
    const json = JSON.parse(text);
    console.log(json);
})

// setInterval(() => {
//         socket.send(JSON.stringify({
//         type: "like",
//         user: "name",
//         content: 'kek'
//         }))
//     }, 1000)

// SPAWN with numbers

setInterval(() => {
        socket.send(JSON.stringify({
        type: "chat",
        user: "name",
        content: 'свет кекс ыц'
        }))
    }, 10000)

// setInterval(() => {
//         socket.send(JSON.stringify({
//         type: "chat",
//         user: "name",
//         content: 'свет кекс ооо'
//         }))
//     }, 10000)

// setInterval(() => {
//         socket.send(JSON.stringify({
//         type: "chat",
//         user: "name",
//         content: 'тьма кекс'
//         }))
//     }, 10000)

// setInterval(() => {
//         socket.send(JSON.stringify({
//         type: "chat",
//         user: "name",
//         content: 'тьма кекс рать'
//         }))
//     }, 10000)

// DESPAWN with numbers

setInterval(() => {
        socket.send(JSON.stringify({
        type: "chat",
        user: "name",
        content: 'свет эдит ыц'
        }))
    }, 10000)

// setInterval(() => {
//         socket.send(JSON.stringify({
//         type: "chat",
//         user: "name",
//         content: 'свет эдит ооо'
//         }))
//     }, 10000)

// setInterval(() => {
//         socket.send(JSON.stringify({
//         type: "chat",
//         user: "name",
//         content: 'тьма эдит'
//         }))
//     }, 10000)

// setInterval(() => {
//         socket.send(JSON.stringify({
//         type: "chat",
//         user: "name",
//         content: 'тьма эдит рать'
//         }))
//     }, 10000)












// setInterval(() => {
//         socket.send(JSON.stringify({
//         type: "chat",
//         user: "name",
//         content: 'клутой!'
//         }))
//     }, 4000)

// setInterval(() => {
//         socket.send(JSON.stringify({
//         type: "gift",
//         user: "name",
//         content: '5655'
//         }))
//     }, 40000)

// setInterval(() => {
//         socket.send(JSON.stringify({
//         type: "follow",
//         user: "name",
//         content: '5655'
//         }))
//     }, 20000)