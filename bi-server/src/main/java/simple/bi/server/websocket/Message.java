package simple.bi.server.websocket;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
  private int code;
  private String message;
  private String status;
  private String data;
}
