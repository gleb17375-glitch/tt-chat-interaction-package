import { ControlEvent, TikTokLiveConnection, WebcastEvent } from 'tiktok-live-connector';
import WebSocket from "ws";

const socket = new WebSocket("ws://localhost:8080");

socket.on("open", () => {
    console.log("connected to server");
});

const streamerUniqueId = process.env.STREAMER_ID || ''

const connection = new TikTokLiveConnection(streamerUniqueId, {});

//@ts-expect-error
connection.on(ControlEvent.ERROR, ({ info, exception }) => {
    console.error(info, exception);
});

//@ts-expect-error
connection.on(WebcastEvent.CHAT, (event) => {
    // console.log(`${event.user?.displayId ?? 'unknown'}: ${event.content}`);
    // console.log("<<< BEGIN <<<")
    // console.log(WebcastEvent.CHAT)
    // console.log(event.user?.displayId ?? 'unknown')
    // console.log(event.content)
    // console.log("<<< END <<<")

    socket.send(JSON.stringify({
        type: WebcastEvent.CHAT,
        user: event.user?.displayId ?? 'unknown',
        content: event.content,
    }))
});

//@ts-expect-error
connection.on(WebcastEvent.GIFT, (event) => {
    // console.log(`${event.user?.displayId ?? 'unknown'} sent gift ${event.giftId}`);
    // console.log("<<< BEGIN <<<")
    // console.log(WebcastEvent.GIFT)
    // console.log(event.user?.displayId ?? 'unknown')
    // console.log(event.giftId)
    // console.log("<<< END <<<")

    socket.send(JSON.stringify({
        type: WebcastEvent.GIFT,
        user: event.user?.displayId ?? 'unknown',
        content: event.giftId,
    }))
});

//@ts-expect-error
connection.on(WebcastEvent.LIKE, (event) => {
    // console.log(`${event.user?.displayId ?? 'unknown'} sent ${event.count} likes (${event.total} total)`);
    // console.log("<<< BEGIN <<<")
    // console.log(WebcastEvent.LIKE)
    // console.log(event.user?.displayId ?? 'unknown')
    // console.log(event.total)
    // console.log("<<< END <<<")

    socket.send(JSON.stringify({
        type: WebcastEvent.LIKE,
        user: event.user?.displayId ?? 'unknown',
        content: event.total,
    }))
});

//@ts-expect-error
connection.on(WebcastEvent.FOLLOW, (event) => {
    // console.log(`${event.user?.displayId ?? 'unknown'} now follower`);
    // console.log("<<< BEGIN <<<")
    // console.log(WebcastEvent.FOLLOW)
    // console.log(event.user?.displayId ?? 'unknown')
    // console.log("<<< END <<<")

    socket.send(JSON.stringify({
        type: WebcastEvent.FOLLOW,
        user: event.user?.displayId ?? 'unknown',
        content: 'no content',
    }))
});

//@ts-expect-error
connection.on(WebcastEvent.SHARE, (event) => {
    // console.log(`${event.user?.displayId ?? 'unknown'} shared`);
    // console.log("<<< BEGIN <<<")
    // console.log(WebcastEvent.SHARE)
    // console.log(event.user?.displayId ?? 'unknown')
    // console.log("<<< END <<<")

    socket.send(JSON.stringify({
        type: WebcastEvent.SHARE,
        user: event.user?.displayId ?? 'unknown',
        content: 'no content',
    }))
});

try {
    const state = await connection.connect();
    console.log(`Connected to room ${state.roomId}`);
} catch (error) {
    console.error('Could not connect', error);
}

process.once('SIGINT', () => {
    void connection.disconnect();
});
