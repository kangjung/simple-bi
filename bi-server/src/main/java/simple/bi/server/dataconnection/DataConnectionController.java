package simple.bi.server.dataconnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import simple.bi.jdbc.ConnectorFactory;
import simple.bi.jdbc.SupportedConnections;
import simple.bi.server.datasource.DataSourceEntity;
import simple.bi.server.exception.BadRequestException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dataconnection")
public class DataConnectionController {

  private static final Logger logger;

  static {
    logger = LoggerFactory.getLogger(DataConnectionController.class);
  }

  @Autowired
  private DataConnectionRepository dataConnectionRepository;

  @GetMapping
  @ResponseBody
  public List<DataConnectionEntity> getDataConnections() {
    return this.dataConnectionRepository.findAll();
  }

  @GetMapping("{id}")
  @ResponseBody
  public DataConnectionEntity getDataConnection(@PathVariable("id") long id) {
    return this.dataConnectionRepository
      .findById(id)
      .orElseThrow(() -> new BadRequestException("DataConnection Not Found."));
  }

  @GetMapping("supportedconnection")
  @ResponseBody
  public SupportedConnections[] getSupportedConnections() {
    return SupportedConnections.values();
  }

  @PostMapping("connection")
  @ResponseBody
  public Map<String, Object> testConnection(@RequestBody @Valid DataConnectionDTO dataConnectionDTO) {
    Map<String, Object> checkMap = new HashMap<>();
    try {
      ConnectorFactory.create(
        dataConnectionDTO.getUrl(),
        dataConnectionDTO.getUserName(),
        dataConnectionDTO.getPassword(),
        dataConnectionDTO.getDbType()
      ).testConnection();
      checkMap.put("connected", true);
      checkMap.put("message", "Connection Successful!");
    } catch (Exception e) {
      checkMap.put("connected", false);
      checkMap.put("message", e.getMessage());
      logger.error(e.getMessage(), e);
    }
     return checkMap;
  }

  @PostMapping
  @ResponseBody
  public DataConnectionEntity addDataConnection(@RequestBody @Valid DataConnectionDTO dataConnectionDTO) {
    return this.dataConnectionRepository.save(
      DataConnectionEntity.builder()
        .name(dataConnectionDTO.getName())
        .url(dataConnectionDTO.getUrl())
        .userName(dataConnectionDTO.getUserName())
        .password(dataConnectionDTO.getPassword()).build()
    );
  }

  @DeleteMapping("{id}")
  @ResponseBody
  public void deleteDataConnection(@PathVariable("id") List<Long> ids) {
    for (Long id : ids) {
      this.dataConnectionRepository.deleteById(id);
    }
  }
}
