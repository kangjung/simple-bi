package simple.bi.server.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import simple.bi.jdbc.AbstractJdbcConnector;
import simple.bi.jdbc.ConnectorFactory;
import simple.bi.server.dataconnection.DataConnectionEntity;
import simple.bi.server.dataconnection.DataConnectionRepository;
import simple.bi.server.exception.BadRequestException;

import java.util.Hashtable;
import java.util.Map;

@Controller
public class MessageController {
  private static final Logger logger;
  private static final Map<Long, AbstractJdbcConnector> connectorMap;

  static {
    logger = LoggerFactory.getLogger(MessageController.class);
    connectorMap = new Hashtable<>(); // synchronize.
  }

  @Autowired
  private DataConnectionRepository dataConnectionRepository;

  @SubscribeMapping("{dataConnectionId}")
  public void initDataConnection(
    @DestinationVariable("dataConnectionId") long dataConnectionId
  ) {
    DataConnectionEntity dataConnectionEntity = this.dataConnectionRepository
      .findById(dataConnectionId)
      .orElseThrow(() -> new BadRequestException("DataConnection Not Found."));
    if (connectorMap.containsKey(dataConnectionEntity.getId())) {
      return;
    }
    connectorMap.put(dataConnectionEntity.getId(), ConnectorFactory.create(
      dataConnectionEntity.getUrl(),
      dataConnectionEntity.getUserName(),
      dataConnectionEntity.getPassword(),
      dataConnectionEntity.getDbType()
    ));
  }

  @MessageExceptionHandler
  @SendTo("/sqleditor/{dataConnectionId}")
  public Message handleException(Throwable t) {
    logger.error(t.getMessage(), t);
    return Message.builder()
      .messageType(MessageType.ERROR)
      .message(t.getMessage())
      .error(true)
      .build();
  }
}
