
package name.viewerunity.adventure;

import net.minecraft.server.MinecraftServer;

public class AdventureEventDispatcher {

    public void handleEvent(String event, MinecraftServer server) {
        if (server == null || event == null) {
            return;
        }

        server.execute(() -> {
            switch (event.toUpperCase()) {
                case "INTRO" ->
                    handleIntroduction(server);

                case "HEAL" ->
                    handleHealing(server);

                case "MINIBOSS" ->
                    handleMiniBoss(server);

                case "EQUIPMENT" ->
                    handleEquipment(server);

                case "ZOMBIE_WAVE" ->
                    handleZombieWave(server);

                default ->
                    System.out.println("Unknown adventure event: " + event);
            }
        });
    }

    private void handleIntroduction(MinecraftServer server) {
        // TODO: Introduction message
    }

    private void handleHealing(MinecraftServer server) {
        // TODO: Healing effect
    }

    private void handleMiniBoss(MinecraftServer server) {
        // TODO: Spawn mini-boss
    }

    private void handleEquipment(MinecraftServer server) {
        // TODO: Give enchanted bow and arrows
    }

    private void handleZombieWave(MinecraftServer server) {
        // TODO: Spawn zombie wave
    }
}
