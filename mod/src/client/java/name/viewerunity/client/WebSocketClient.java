package name.viewerunity.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletionStage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebSocketClient implements WebSocket.Listener {
    public static final String MOD_ID = "viewerunity";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private WebSocket webSocket;

    public void connect(String url) {
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
    public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {
        LOGGER.info("WebSocket client recived: " + data.remaining() + " bytes");

        String json = StandardCharsets.UTF_8.decode(data).toString();

        LOGGER.info("WebSocket client recived: " + json + " string");

        ObjectMapper mapper = new ObjectMapper(null);

        Message msg;
        try {
            msg = mapper.readValue(json, Message.class);
            LOGGER.info("WebSocket client recived: " + msg.test() + " field");
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        webSocket.request(1);
        return null;
    }
}
