package simple.bi.server.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import simple.bi.server.dataconnection.DataConnectionEntity;
import simple.bi.server.dataconnection.DataConnectionRepository;
import simple.bi.server.exception.BadRequestException;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MessageController {
  private static final Logger logger;

  static {
    logger = LoggerFactory.getLogger(MessageController.class);
  }

  @Autowired
  private DataConnectionRepository dataConnectionRepository;

  @SubscribeMapping("{dataConnectionId}")
  public DataConnectionEntity initDataConnection(
    @DestinationVariable("dataConnectionId") long dataConnectionId
  ) {
    return this.dataConnectionRepository
      .findById(dataConnectionId)
      .orElseThrow(() -> new BadRequestException("DataConnection Not Found."));
  }

  @MessageExceptionHandler
  @SendTo("/sqleditor/{dataConnectionId}")
  public Message handleException(Throwable t) {
    logger.error(t.getMessage(), t);
    return Message.builder()
      .code(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
      .message(t.getMessage())
      .status("error")
      .build();
  }
}