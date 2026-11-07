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
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
        if (ViewerUnity.serverInstance == null) return null;

        LOGGER.info("WebSocket client recived: " + data.remaining() + " bytes");

        String json = StandardCharsets.UTF_8.decode(data).toString();

        LOGGER.info("WebSocket client recived: " + json + " string");

        Message msg = this.mapper.readValue(json, Message.class);
        LOGGER.info("WebSocket client recived: " + msg.type() + " field");
        LOGGER.info("WebSocket client recived: " + msg.user() + " field");
        LOGGER.info("WebSocket client recived: " + msg.content() + " field");

        if(msg.type().contains("chat")) {
            ViewerUnity.serverInstance.execute(() -> {
                ViewerUnity.serverInstance.getPlayerList().broadcastSystemMessage(Component.literal(msg.user() + " | " + msg.content())
            .withStyle(ChatFormatting.BLUE), false);
            });
        }

        if(msg.type().contains("like")) {
            MobEffectInstance regen = new MobEffectInstance(
                    MobEffects.REGENERATION, 
                    5 * 20, 
                    1, 
                    false, 
                    true, 
                    true);

            ViewerUnity.serverInstance.execute(() -> {
                ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
                    player -> {
                        player.addEffect(regen);
                    }
                );
            });
        }

        if(msg.type().contains("follow")) {
            ViewerUnity.serverInstance.execute(() -> {
                ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
                    player -> {
                        ServerLevel level = player.level();
                        BlockPos spawnPos = BlockPos.containing(player.getX() + 1, player.getY(), player.getZ());

                        Villager villager = EntityTypes.VILLAGER.spawn(level, spawnPos, EntitySpawnReason.COMMAND);

                        if (villager != null) {
                            villager.setCustomName(Component.literal(msg.user()));
                            villager.setCustomNameVisible(true);
                        }
                    }
                );
            });
        }

        if(msg.type().contains("gift")) {
            ViewerUnity.serverInstance.execute(() -> {
                ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
                    player -> {
                        // if (msg.content().contains("7934")) {
                        //     ItemStack goldenApple = new ItemStack(Items.GOLDEN_APPLE);
                        //     boolean putedIn = player.getInventory().add(goldenApple);
                        //     if (!putedIn) player.drop(goldenApple, false, null);
                        //     player.sendSystemMessage(Component.literal("§d+ 🌹 HEART ME!"));
                        //     return;
                        // } 
                        ItemStack rose = new ItemStack(Items.POPPY);
                        rose.set(DataComponents.CUSTOM_NAME, Component.literal(msg.user()));
                        boolean putedIn = player.getInventory().add(rose);
                        if (!putedIn) player.drop(rose, false, null);
                        player.sendSystemMessage(Component.literal("§d+ 🌹 Rose!"));
                    }
                );
            });
        }


        webSocket.request(1);
        return null;
    }
}
