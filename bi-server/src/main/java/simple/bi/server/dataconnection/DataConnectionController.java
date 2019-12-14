package simple.bi.server.dataconnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import simple.bi.jdbc.ConnectorFactory;
import simple.bi.jdbc.SupportedConnections;
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
            .dbType(dataConnectionDTO.getDbType())
            .userName(dataConnectionDTO.getUserName())
            .password(dataConnectionDTO.getPassword()).build()
    );
  }

  @PutMapping("{id}")
  @ResponseBody
  public DataConnectionEntity setDataConnection(@PathVariable("id") long id,
    @RequestBody @Valid DataConnectionDTO dataConnectionDTO
  ) {
    DataConnectionEntity dataConnectionEntity =
      this.dataConnectionRepository
        .findById(id)
        .orElseThrow(() -> new BadRequestException("DataConnection Not Found."));
    dataConnectionEntity.setUserName(dataConnectionDTO.getUserName());
    dataConnectionEntity.setPassword(dataConnectionDTO.getPassword());
    dataConnectionEntity.setDbType(dataConnectionDTO.getDbType());
    dataConnectionEntity.setName(dataConnectionDTO.getName());
    dataConnectionEntity.setUrl(dataConnectionDTO.getUrl());
    this.dataConnectionRepository.save(dataConnectionEntity);
    return dataConnectionEntity;
  }

  @DeleteMapping("{id}")
  @ResponseBody
  public void deleteDataConnection(@PathVariable("id") long id) {
    this.dataConnectionRepository.deleteById(id);
  }
}
