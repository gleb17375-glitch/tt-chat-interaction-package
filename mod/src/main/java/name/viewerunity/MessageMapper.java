package name.viewerunity;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import tools.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageMapper {
    public static final String MOD_ID = "viewerunity";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private ObjectMapper mapper;

    MessageMapper() {
        this.mapper = new ObjectMapper();
    }

    public Message mapData(ByteBuffer data) {
        LOGGER.info("WebSocket client recived: " + data.remaining() + " bytes");

        String json = StandardCharsets.UTF_8.decode(data).toString();

        LOGGER.info("WebSocket client recived: " + json + " string");

        Message msg = this.mapper.readValue(json, Message.class);
        LOGGER.info("WebSocket client recived: " + msg.type() + " field");
        LOGGER.info("WebSocket client recived: " + msg.user() + " field");
        LOGGER.info("WebSocket client recived: " + msg.content() + " field");

        return msg;
    }
}
