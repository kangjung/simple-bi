package simple.bi.server.websocket;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
  private String message;
  private String data;
  private MessageType messageType;
  private boolean error;
}
