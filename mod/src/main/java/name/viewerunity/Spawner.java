package name.viewerunity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class Spawner {
    public static final void spawnTnt(ServerPlayer player) {
        ServerLevel level = player.level();
        BlockPos spawnPos = BlockPos.containing(player.getX() + 1, player.getY(), player.getZ());
                        
        PrimedTnt tnt = new PrimedTnt(level, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), player);

        tnt.setFuse(80);

        level.addFreshEntity(tnt);
    }

    public static final void spawnCreeper(ServerPlayer player) {
        if (player == null) return;
        ServerLevel level = player.level();
        BlockPos spawnPos = BlockPos.containing(player.getX() + 1, player.getY(), player.getZ());

        // 2. Спавним обычного крипера
        Creeper creeper = EntityTypes.CREEPER.spawn(level, spawnPos, EntitySpawnReason.COMMAND);
        if (creeper != null) {
            level.addFreshEntity(creeper);

            // 3. Призываем молнию в ту же позицию, чтобы зарядить крипера
            LightningBolt lightning = EntityTypes.LIGHTNING_BOLT.spawn(level, spawnPos, EntitySpawnReason.COMMAND);
            if (lightning != null) {
                level.addFreshEntity(lightning);
            }
        }
    }

    public static final void spawnFireball(ServerPlayer player) {
        if (player == null) return;
        ServerLevel level = player.level();
        BlockPos spawnPos = BlockPos.containing(player.getX(), player.getY() + 1, player.getZ());

        LargeFireball fireball = EntityTypes.FIREBALL.spawn(level, spawnPos, EntitySpawnReason.COMMAND);

    if (fireball != null) {
        // 1. Устанавливаем владельца (чтобы шар не задел самого игрока при спавне)
        fireball.setOwner(player);

        // 3. Направление: туда, куда смотрит игрок
        Vec3 lookDirection = player.getLookAngle();

        // 4. Задаём скорость (1.0 — стандартная скорость, можно увеличить)
        fireball.setDeltaMovement(lookDirection.scale(.75));

        // 5. Спавним
        level.addFreshEntity(fireball);

        }
    }

    public static final void spawnFireball(ServerPlayer player, Entity owner) {
        if (player == null) return;
        ServerLevel level = player.level();
        BlockPos spawnPos = BlockPos.containing(owner.getX(), owner.getY() + 1, owner.getZ());

        LargeFireball fireball = EntityTypes.FIREBALL.spawn(level, spawnPos, EntitySpawnReason.COMMAND);

    if (fireball != null) {
        // 1. Устанавливаем владельца (чтобы шар не задел самого игрока при спавне)
        fireball.setOwner(player);

        // 3. Направление: туда, куда смотрит игрок
        Vec3 lookDirection = owner.getLookAngle();

        // 4. Задаём скорость (1.0 — стандартная скорость, можно увеличить)
        fireball.setDeltaMovement(lookDirection.scale(.75));

        // 5. Спавним
        level.addFreshEntity(fireball);

        }
    }

    public static final void spawnGolem(ServerPlayer player, MutableComponent name) {
        ServerLevel level = player.level();
        BlockPos spawnPos = BlockPos.containing(player.getX() + 1, player.getY(), player.getZ());

        IronGolem ironGolem = EntityTypes.IRON_GOLEM.spawn(level, spawnPos, EntitySpawnReason.COMMAND);

        if (ironGolem != null) {
            ironGolem.setCustomName(name);
            // ironGolem.setPlayerCreated(true);
        }
    }

    public static final void spawnVillager(ServerPlayer player, MutableComponent name) {
        ServerLevel level = player.level();
        BlockPos spawnPos = BlockPos.containing(player.getX() + 1, player.getY(), player.getZ());

        Villager villager = EntityTypes.VILLAGER.spawn(level, spawnPos, EntitySpawnReason.COMMAND);

        if (villager != null) {
            villager.setCustomName(name);
            villager.setCustomNameVisible(true);
        }
    }

    public static final void spawnZombie(ServerPlayer player, MutableComponent name) {
        ServerLevel level = player.level();
        BlockPos spawnPos = BlockPos.containing(player.getX() + 1, player.getY(), player.getZ());

        Zombie zombie = EntityTypes.ZOMBIE.spawn(level, spawnPos, EntitySpawnReason.COMMAND);

        if (zombie != null) {
            zombie.setCustomName(name);
            zombie.setCustomNameVisible(true);
        }
    }

    public static final void spawnRose(ServerPlayer player, MutableComponent name) {
                        // if (msg.content().contains("7934")) {
                        //     ItemStack goldenApple = new ItemStack(Items.GOLDEN_APPLE);
                        //     boolean putedIn = player.getInventory().add(goldenApple);
                        //     if (!putedIn) player.drop(goldenApple, false, null);
                        //     player.sendSystemMessage(Component.literal("§d+ 🌹 HEART ME!"));
                        //     return;
                        // } 
        ItemStack rose = new ItemStack(Items.POPPY);
        rose.set(DataComponents.CUSTOM_NAME, name);
        boolean putedIn = player.getInventory().add(rose);
        if (!putedIn) player.drop(rose, false, null);
        player.sendSystemMessage(Component.literal("§d+ 🌹 Rose!"));   
    }
}
    
    
