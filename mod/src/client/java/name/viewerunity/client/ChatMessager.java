package name.viewerunity.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ChatMessager {
    public static final String MOD_ID = "viewerunity";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void send(String user, String message, ChatFormatting color) {
       
        Minecraft client = Minecraft.getInstance();

        if (client.player == null) {
            return;
        }

        client.gui.hud.getChat().addClientSystemMessage(Component.literal(user + " | " + message).withStyle(color));
    }
}
