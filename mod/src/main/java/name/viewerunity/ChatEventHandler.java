package name.viewerunity;

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
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ChatEventHandler {
    public static final String MOD_ID = "viewerunity";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final void HandleEvent(Message msg) {
        if(msg == null) return;

        try {
        if(msg.type().contains("chat") &&  msg.content().contains("бебебе!")) {
            ViewerUnity.serverInstance.execute(() -> {
                ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
                    player -> {
                        // change to spawn creaper
                        // + add to spawn 
                        ServerLevel level = player.level();
                        BlockPos spawnPos = BlockPos.containing(player.getX() + 1, player.getY(), player.getZ());
                        
                        PrimedTnt tnt = new PrimedTnt(level, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), player);

                        tnt.setFuse(80);

                        level.addFreshEntity(tnt);

                        player.sendSystemMessage(Component.literal("§d+ Бебебе!"));

                    }
                );
            //     ViewerUnity.serverInstance.getPlayerList().broadcastSystemMessage(Component.literal(formatTo8(msg.user()) + "| " + msg.content())
            // .withStyle(ChatFormatting.BLUE), false);
            });
        }

        if(msg.type().contains("chat") &&  msg.content().contains("клутой!")) {
            ViewerUnity.serverInstance.execute(() -> {
                ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
                    player -> {
                        // change to spawn creaper
                        // + add to spawn 
                        ServerLevel level = player.level();
                        BlockPos spawnPos = BlockPos.containing(player.getX() + 1, player.getY(), player.getZ());
                        
                        PrimedTnt tnt = new PrimedTnt(level, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), player);

                        tnt.setFuse(80);

                        level.addFreshEntity(tnt);

                        player.sendSystemMessage(Component.literal("§d+ клутой!"));

                    }
                );
            //     ViewerUnity.serverInstance.getPlayerList().broadcastSystemMessage(Component.literal(formatTo8(msg.user()) + "| " + msg.content())
            // .withStyle(ChatFormatting.BLUE), false);
            });
        }
    

        if(msg.type().contains("like")) {
            // if (ViewerUnity.isOnCooldown("regen_event_cooldown")) return null;
            ViewerUnity.serverInstance.execute(() -> {

            ViewerUnity.serverInstance.getPlayerList().getPlayers().stream().findAny().ifPresent(
                    player -> { player.addEffect(new MobEffectInstance(
                    MobEffects.REGENERATION, 
                    5 * 20, 
                    1, 
                    false, 
                    true, 
                    true));
                    });
            });
        }

        if(msg.type().contains("OFF__follow")) {
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
                ServerPlayer player = ViewerUnity.serverInstance.getPlayerList().getPlayers().getFirst();
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
                    
                
            });
        }
    } catch (Throwable e) {
        LOGGER.error(e.toString());
    }
    }
    
}
