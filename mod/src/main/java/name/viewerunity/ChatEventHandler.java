package name.viewerunity;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.network.chat.Component;

public class ChatEventHandler {
    public static final String MOD_ID = "viewerunity";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    Spawner spawner = null;

    ChatEventHandler() {
        this.spawner = new Spawner();
    }

    public static boolean getRandPercent(int percent) {
        Random rand = new Random();
        return rand.nextInt(100) <= percent;
    }

    public void HandleEvent(Message msg, int wordCount, boolean isFriend) {
        if (msg == null)
            return;
        LOGGER.error("HANDLE_EVENT___ " + msg + " " + wordCount + " " + isFriend);

        try {
            if (msg.type().contains("chat") && wordCount >= 3) {
                if (isFriend) {
                    ViewerUnity.serverInstance.execute(() -> {
                        ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
                                player -> {
                                    this.spawner.spawnGolem(player, Component.literal(msg.user()));
                                    this.spawner.spawnGolem(player, Component.literal(msg.user()));

                                    this.spawner.killZombie(player, Component.literal(msg.user()), 5);
                                });
                    });
                }
                if (!isFriend) {
                    ViewerUnity.serverInstance.execute(() -> {
                        ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
                                player -> {
                                });
                    });
                    ViewerUnity.serverInstance.execute(() -> {
                        ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
                                player -> {

                                // this.spawner.killGolem(player, Component.literal(msg.user()), 1);
                                 if (player == null)
                                        return;
                                    this.spawner.spawnZombie(player, Component.literal(msg.user()));
                                    this.spawner.spawnZombie(player, Component.literal(msg.user()));
                                    this.spawner.spawnZombie(player, Component.literal(msg.user()));
                                    this.spawner.spawnZombie(player, Component.literal(msg.user()));
                                    this.spawner.spawnZombie(player, Component.literal(msg.user()));
                                    this.spawner.spawnZombie(player, Component.literal(msg.user()));
                                    this.spawner.spawnZombie(player, Component.literal(msg.user()));
                                    this.spawner.spawnZombie(player, Component.literal(msg.user()));
                                    this.spawner.spawnZombie(player, Component.literal(msg.user()));
                                    this.spawner.spawnZombie(player, Component.literal(msg.user()));
                                    this.spawner.spawnZombie(player, Component.literal(msg.user()));
                                    this.spawner.spawnZombie(player, Component.literal(msg.user()));
                                    this.spawner.spawnZombie(player, Component.literal(msg.user()));
                                });
                    });
                }

            }
        } catch (

        Throwable e) {
            LOGGER.error(e.toString());
        }
    }

}
