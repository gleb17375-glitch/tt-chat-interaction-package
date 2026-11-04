import { ControlEvent, TikTokLiveConnection, WebcastEvent } from 'tiktok-live-connector';

const connection = new TikTokLiveConnection('milana.vim27', {});

//@ts-expect-error
connection.on(ControlEvent.ERROR, ({ info, exception }) => {
    console.error(info, exception);
});

//@ts-expect-error
connection.on(WebcastEvent.CHAT, (event) => {
    console.log(`${event.user?.displayId ?? 'unknown'}: ${event.content}`);
});

//@ts-expect-error
connection.on(WebcastEvent.GIFT, (event) => {
    console.log(`${event.user?.displayId ?? 'unknown'} sent gift ${event.giftId}`);
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