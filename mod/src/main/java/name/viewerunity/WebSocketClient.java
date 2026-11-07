package name.viewerunity;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletionStage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import tools.jackson.databind.ObjectMapper;

public class WebSocketClient implements WebSocket.Listener {
    public static final String MOD_ID = "viewerunity";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private WebSocket webSocket;
    private ObjectMapper mapper;

    public void connect(String url) {
        this.mapper = new ObjectMapper();

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

        Message msg = this.mapper.readValue(json, Message.class);
        LOGGER.info("WebSocket client recived: " + msg.type() + " field");
        LOGGER.info("WebSocket client recived: " + msg.user() + " field");
        LOGGER.info("WebSocket client recived: " + msg.content() + " field");

        if(msg.type().contains("chat")) {
            // Minecraft.getInstance().execute(() -> {
            //     ChatMessager.send(msg.user(), msg.content(), ChatFormatting.BLUE);
            // });
            ViewerUnity.serverInstance.execute(() -> {
                ViewerUnity.serverInstance.getPlayerList().broadcastSystemMessage(Component.literal(msg.user() + " | " + msg.content())
            .withStyle(ChatFormatting.BLUE), false);
            });
        }


        webSocket.request(1);
        return null;
    }
}
