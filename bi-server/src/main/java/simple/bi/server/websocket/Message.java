package simple.bi.server.websocket;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class Message {
  @Builder.Default
  private String message = "OK";
  private Object data;
  private MessageType messageType;
  private boolean error;
}
