package name.viewerunity;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionStage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.network.chat.Component;

public class WebSocketClient implements WebSocket.Listener {
    public static final String MOD_ID = "viewerunity";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final int TOKEN_LIMIT = 13;

    private WebSocket webSocket;
    private MessageMapper mapper;
    private ChatEventHandler eventHandler;
    private Map<String, Integer> counters = new HashMap<>();

    public void connect(String url) {
        this.mapper = new MessageMapper();
        this.eventHandler = new ChatEventHandler();

        HttpClient.newHttpClient()
                .newWebSocketBuilder()
                .buildAsync(URI.create(url), this)
                .thenAccept(ws -> this.webSocket = ws);
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        LOGGER.info("WebSocket client connected");
        webSocket.request(1);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        // TODO Auto-generated method stub
        Listener.super.onError(webSocket, error);
        LOGGER.error("WebSocket error" + error);
    }

    @Override
    public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {
        if (ViewerUnity.serverInstance == null) {
            webSocket.request(1);
            return null;
        }

        Message msg = this.mapper.mapData(data);
        if (msg == null) {
            webSocket.request(1);
            return null;
        }


        if (!msg.type().contains("chat")
                && !msg.type().contains("like")
                && !msg.type().contains("gift")
                && !msg.type().contains("follow")
                && !msg.type().contains("share")) {
            webSocket.request(1);
            return null;
        }

        LOGGER.error("ENTERED: " + msg.user());
        boolean hasExceded = counters.getOrDefault(msg.user(), 0) > TOKEN_LIMIT ? true : false;
        // if (hasExceded) {
        //     LOGGER.error("User exceded token amount: " + msg.user());
        //     webSocket.request(1);
        //     return null;
        // }
        counters.merge(msg.user(), 1, Integer::sum);
        LOGGER.error("NO_EXCEDED: " + msg.user());

        if(msg.type().contains("follow") || msg.type().contains("share") || counters.getOrDefault(msg.user(), 0) == TOKEN_LIMIT) {
            counters.merge(msg.user(), TOKEN_LIMIT, Integer::sum);
            ViewerUnity.serverInstance.execute(() -> {
                ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
                    player -> {
                        player.sendSystemMessage(Component.literal("§d+ " + msg.user() + " теперь друг"));
                        });
                    });
        }

        Set<String> items = new HashSet<String>(Arrays.asList(msg.content().toLowerCase().split("\\s+")));
        if (msg.type().contains("chat") && items.size() < 3) {
            webSocket.request(1);
            return null;
        }

        LOGGER.error("MESSAGE LONG: " + msg.user());
        this.eventHandler.HandleEvent(msg, items.size(), hasExceded);

        webSocket.request(1);
        return null;
    }
}
