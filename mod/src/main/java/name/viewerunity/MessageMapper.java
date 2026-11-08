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
        try {
            String json = StandardCharsets.UTF_8.decode(data).toString();
            Message msg = this.mapper.readValue(json, Message.class);
            return msg;
        } catch (Exception e) {
            
        }
        return null;
    }
}
