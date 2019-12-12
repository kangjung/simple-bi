package simple.bi.server.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import simple.bi.server.csvimport.CsvImportService;
import simple.bi.server.exception.BadRequestException;
import simple.bi.server.fileupload.FileUploadService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/datasource")
public class DataSourceController {
  @Autowired
  private CsvImportService csvImportService;

  @Autowired
  private FileUploadService fileUploadService;

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
  public void upload(MultipartHttpServletRequest req, @RequestParam Map<String, Object> paramMap) {
    fileUploadService.upload(req.getFile("csvFile"));
  }
}
