package simple.bi.server.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import simple.bi.server.csv.CsvImportService;
import simple.bi.server.exception.BadRequestException;

import java.util.List;

@RestController
@RequestMapping("/api/datasource")
public class DataSourceController {
  @Autowired
  private CsvImportService csvImportService;

  @Autowired
  private DataSourceRepository dataSourceRepository;

  @GetMapping
  @ResponseBody
  public List<DataSourceEntity> getDataSources() {
    return this.dataSourceRepository.findAll();
  }

  @GetMapping("{id}")
  @ResponseBody
  public DataSourceEntity getDatasource(@PathVariable("id") long id) {
    return this.dataSourceRepository
      .findById(id)
      .orElseThrow(() -> new BadRequestException("Datasource Not Found."));
  }

  @PostMapping("/upload")
  @ResponseBody
  public void upload(MultipartHttpServletRequest req) {
    // TODO(kuckjwi): implementation.
  }
}
