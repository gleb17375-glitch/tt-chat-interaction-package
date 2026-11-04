package name.viewerunity.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewerUnityClient implements ClientModInitializer {
	public static final String MOD_ID = "viewerunity";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		LOGGER.info("Hello client world!");
		
		// LevelExtractionEvents.END_EXTRACTION.register(this::extractWaypoint);
		LevelRenderEvents.AFTER_TRANSLUCENT_TERRAIN.register(this::renderAndDrawWaypoint);

		WebSocketClient wsc = new WebSocketClient();
		wsc.connect("ws://localhost:8080");

	}

	private void renderAndDrawWaypoint(LevelRenderContext context) {
		Minecraft.getInstance().gui.setScreen(
  			new CustomScreen(Component.empty())
		);
	}
}