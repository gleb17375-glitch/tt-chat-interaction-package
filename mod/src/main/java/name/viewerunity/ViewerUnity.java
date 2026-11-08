package name.viewerunity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewerUnity implements ModInitializer {
	public static final String MOD_ID = "viewerunity";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static MinecraftServer serverInstance = null;
	public static WebSocketClient wsc = null;

	// Храним время последнего срабатывания для каждого типа события
    private static final Map<String, Long> cooldowns = new ConcurrentHashMap<>();
    private static final long COOLDOWN_MS = 5_000; // 5 секунд

    public static boolean isOnCooldown(String key) {
        long now = System.currentTimeMillis();
        Long last = cooldowns.get(key);
        if (last != null && now - last < COOLDOWN_MS) {
            return true; // ещё на кулдауне
        }
        cooldowns.put(key, now); // обновляем время
        return false;
    }

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
                serverInstance = server;
				wsc = new WebSocketClient();
				wsc.connect("ws://localhost:8080");
        });

		ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
			serverInstance = null;
		});
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
