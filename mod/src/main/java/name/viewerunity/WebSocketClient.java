package name.viewerunity;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionStage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.network.chat.Component;

public class WebSocketClient implements WebSocket.Listener {
    public static final String MOD_ID = "viewerunity";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private WebSocket webSocket;
    private MessageMapper mapper;
    private ChatEventHandler eventHandler;
    private Map<String, Integer> counters = new HashMap<>();
    

    String player_team_word = new String("кофе");
    String enemy_team_word = new String("чай");
    String add_word = new String("кекс");
    String remove_word = new String("edit");

    boolean changingNames = false;

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
        if(msg == null) {
            webSocket.request(1);
            return null;
        }

        if (
            !msg.type().contains("chat") 
            && !msg.type().contains("like") 
            && !msg.type().contains("gift") 
            && !msg.type().contains("follow")
            && !msg.type().contains("share")
        ) {
            webSocket.request(1);
            return null;
        }
        if (counters.getOrDefault(msg.user(), 0) > 100) {
            LOGGER.error("User exceded token amount: " + msg.user());
            webSocket.request(1);
            return null;
        }
        counters.merge(msg.user(), 1, Integer::sum);

        if (changingNames) {
            if(msg.type().contains("chat")) {
                Set<String> items = new HashSet<String>(Arrays.asList(msg.content().split("\\s+")));
                List<String> list = new ArrayList<>(items);
                // LOGGER.error("word set changed:" + msg_words.length + msg_words[0]);
                if(list.size() < 2)  {
                    webSocket.request(1);
                    return null;
                }
                add_word = list.get(0);
                remove_word = list.get(1);

                // LOGGER.error("word set changed:" + player_team_word + enemy_team_word + add_word + remove_word);
                ViewerUnity.serverInstance.execute(() -> {
                        ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
                                player -> {
                                    player.sendSystemMessage(Component.literal("§d+ 🌹 Новые слова!"));   
                                    player.sendSystemMessage(Component.literal("§d+ Добавить: " + add_word));
                                    player.sendSystemMessage(Component.literal("§d+ Удалить: " + remove_word));      
                                });
                    });
                
                this.changingNames = false;
            }
        }

        if(msg.type().contains("follow") && !this.changingNames) {
            this.changingNames = true;
            // LOGGER.error("FOLOWER___. " + player_team_word + enemy_team_word + add_word + remove_word);
        }
        if(msg.type().contains("like") && !this.changingNames) {
            this.changingNames = true;
            // LOGGER.error("FOLOWER___. " + player_team_word + enemy_team_word + add_word + remove_word);
        }

        this.eventHandler.HandleEvent(msg, player_team_word, enemy_team_word,  add_word, remove_word );

        webSocket.request(1);
        return null;
    }
}
