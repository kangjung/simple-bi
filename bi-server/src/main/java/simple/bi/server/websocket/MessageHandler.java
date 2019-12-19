package simple.bi.server.websocket;

public interface MessageHandler {
  Message onMessage(long userId, long dataConnectionId, Message message) throws Exception;
}
