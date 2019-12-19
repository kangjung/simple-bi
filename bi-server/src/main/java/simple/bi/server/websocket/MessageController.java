package simple.bi.server.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import simple.bi.jdbc.AbstractJdbcConnector;
import simple.bi.jdbc.ConnectorFactory;
import simple.bi.server.SimpleBiConfig;
import simple.bi.server.dataconnection.DataConnectionEntity;
import simple.bi.server.dataconnection.DataConnectionRepository;
import simple.bi.server.exception.BadRequestException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Controller
public class MessageController implements MessageHandler {
  private static final Logger logger;
  private static final Map<Long, AbstractJdbcConnector> connectorMap;

  static {
    logger = LoggerFactory.getLogger(MessageController.class);
    connectorMap = new HashMap<>();
  }

  @Autowired
  private DataConnectionRepository dataConnectionRepository;

  @Autowired
  private SimpleBiConfig simpleBiConfig;

  @SubscribeMapping("{dataConnectionId}")
  @SendTo("/sqleditor/{dataConnectionId}")
  public Message initDataConnection(
    @DestinationVariable("dataConnectionId") long dataConnectionId
  ) throws SQLException {
    DataConnectionEntity dataConnectionEntity = this.dataConnectionRepository
      .findById(dataConnectionId)
      .orElseThrow(() -> new BadRequestException("DataConnection Not Found."));

    AbstractJdbcConnector connector;

    synchronized (connectorMap) {
      boolean hasConnection = connectorMap.containsKey(dataConnectionEntity.getId());

      if (hasConnection) {
        connector = connectorMap.get(dataConnectionEntity.getId());
      } else {
        connector = ConnectorFactory.create(
            dataConnectionEntity.getUrl(),
            dataConnectionEntity.getUserName(),
            dataConnectionEntity.getPassword(),
            dataConnectionEntity.getDbType()
        );
      }

      try {
        connector.testConnection(false);
      } catch (SQLException e) {
        return Message.builder()
          .messageType(MessageType.INIT_ERROR)
          .message(e.getMessage())
          .error(true)
          .build();
      }

      if (!hasConnection) {
        connectorMap.put(dataConnectionEntity.getId(), connector);
      }
    }

    return Message.builder()
        .messageType(MessageType.INIT)
        .error(false)
        .build();
  }

  @SubscribeMapping("{userId}/{dataConnectionId}")
  @SendTo("/sqleditor/{userId}/{dataConnectionId}")
  public Message getMetaInfo(
    @DestinationVariable("userId") long userId,
    @DestinationVariable("dataConnectionId") long dataConnectionId
  ) throws Exception {
    return Message.builder()
        .messageType(MessageType.META)
        .data(connectorMap.get(dataConnectionId).getTableMap())
        .build();
  }

  @Override
  @MessageMapping("{userId}/{dataConnectionId}")
  @SendTo("/sqleditor/{userId}/{dataConnectionId}")
  public Message onMessage(
    @DestinationVariable("userId") long userId,
    @DestinationVariable("dataConnectionId") long dataConnectionId,
    Message message
  ) throws Exception {
    return switch (message.getMessageType()) {
      case RUN_QUERY -> Message.builder()
        .messageType(MessageType.QUERY_RESULT)
        .data(
          connectorMap.get(dataConnectionId)
            .runQuery(message.getData().toString(), simpleBiConfig.getDefaultResultLimit())
        )
        .build();
      default -> throw new IllegalArgumentException("This message is unknown.");
    };
  }

  @MessageExceptionHandler
  @SendTo("/sqleditor/{userId}/{dataConnectionId}")
  public Message handleException(Throwable t) {
    logger.error(t.getMessage(), t);
    return Message.builder()
      .messageType(MessageType.ERROR)
      .message(t.getMessage())
      .error(true)
      .build();
  }
}
