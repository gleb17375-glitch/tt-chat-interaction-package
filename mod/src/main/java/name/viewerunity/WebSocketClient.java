package name.viewerunity;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletionStage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketClient implements WebSocket.Listener {
    public static final String MOD_ID = "viewerunity";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private WebSocket webSocket;
    private MessageMapper mapper;

    public void connect(String url) {
        this.mapper = new MessageMapper();

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
        this.connect("ws://localhost:8080");
    }

    @Override
    public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {
        if (ViewerUnity.serverInstance == null) return null;
        
        Message msg = this.mapper.mapData(data);

        ChatEventHandler.HandleEvent(msg);

        webSocket.request(1);
        return null;
    }
}
