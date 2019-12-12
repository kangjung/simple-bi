package simple.bi.server.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventHandler {
  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    // TODO(kuckjwi): websocket connected.
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    // TODO(kuckjwi): websocket disconnected.
  }
}
