package name.viewerunity;

import java.beans.EventHandler;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;

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

    public void HandleEvent(Message msg, String player_team_word, String enemy_team_word, String add_word, String remove_word) {
        if (msg == null)
            return;
        // LOGGER.error("HANDLE_EVENT___ " + player_team_word + enemy_team_word + add_word + remove_word);
    
        try {
            if (msg.content().contains(player_team_word)) {
                if (msg.content().contains(add_word)) {
                    ViewerUnity.serverInstance.execute(() -> {
                        ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
                                player -> {
                                    this.spawner.spawnGolem(player, Component.literal(msg.user()));
                                    this.spawner.spawnGolem(player, Component.literal(msg.user()));
                                });
                    });
                }
                if (msg.content().contains(remove_word)) {
                    ViewerUnity.serverInstance.execute(() -> {
                        ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
                                player -> {
                                    this.spawner.killGolem(player, Component.literal(msg.user()), 1);
                                });
                    });
                }
            } else if (msg.content().contains(enemy_team_word)) {
                if (msg.content().contains(add_word)) {
                    ViewerUnity.serverInstance.execute(() -> {
                        ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
                                player -> {
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
                if (msg.content().contains(remove_word)) {
                    ViewerUnity.serverInstance.execute(() -> {
                        ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
                                player -> {
                                    this.spawner.killZombie(player, Component.literal(msg.user()), 5);
                                });
                    });
                }
            }

            // if(msg.type().contains("chat") && msg.content().contains("бебебе!")) {
            // ViewerUnity.serverInstance.execute(() -> {
            // ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
            // player -> {
            // if(getRandPercent(10)) {
            // Spawner.spawnCreeper(player);
            // } else {
            // Spawner.spawnZombie(player, Component.literal(msg.user()));
            // }
            // // player.sendSystemMessage(Component.literal("§d+ Бебебе!"));
            // }
            // );
            // //
            // ViewerUnity.serverInstance.getPlayerList().broadcastSystemMessage(Component.literal(formatTo8(msg.user())
            // + "| " + msg.content())
            // // .withStyle(ChatFormatting.BLUE), false);
            // });
            // }

            // if(msg.type().contains("chat") && msg.content().contains("клутой!")) {
            // ViewerUnity.serverInstance.execute(() -> {
            // ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
            // player -> {
            // Spawner.spawnFireball(player);

            // AABB searchArea = new AABB(
            // player.getX() - 32, -64, player.getZ() - 32,
            // player.getX() + 32, 320, player.getZ() + 32
            // );

            // // 2. Ищем големов только в этой области
            // List<IronGolem> golems = player.level().getEntitiesOfClass(IronGolem.class,
            // searchArea);
            // golems.forEach(golem -> {
            // Spawner.spawnFireball(player, golem);
            // });
            // // player.sendSystemMessage(Component.literal("§d+ клутой!"));

            // }
            // );
            // //
            // ViewerUnity.serverInstance.getPlayerList().broadcastSystemMessage(Component.literal(formatTo8(msg.user())
            // + "| " + msg.content())
            // // .withStyle(ChatFormatting.BLUE), false);
            // });
            // }

            // if(msg.type().contains("like")) {
            // // if (ViewerUnity.isOnCooldown("regen_event_cooldown")) return null;
            // ViewerUnity.serverInstance.execute(() -> {

            // ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
            // player -> { player.addEffect(new MobEffectInstance(
            // MobEffects.REGENERATION,
            // 5 * 20,
            // 1,
            // false,
            // true,
            // true));
            // });
            // });
            // }

            // if(msg.type().contains("OFF__follow")) {
            // ViewerUnity.serverInstance.execute(() -> {
            // ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
            // player -> {
            // Spawner.spawnVillager(player, Component.literal(msg.user()));
            // }
            // );
            // });
            // }

            // if(msg.type().contains("gift")) {
            // ViewerUnity.serverInstance.execute(() -> {
            // ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
            // player -> {
            // Spawner.spawnGolem(player, Component.literal(msg.user()));
            // }
            // );
            // });
            // }
        } catch (

        Throwable e) {
            LOGGER.error(e.toString());
        }
    }

}
