package simple.bi.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import simple.bi.server.datasource.impl.CssImportServiceImpl;

import java.io.IOException;

@SpringBootTest
public class CsvImportTests {
  @Autowired
  private CssImportServiceImpl cssImportService;

  @Test
  void csvImportTest() throws IOException {
    cssImportService.importCSV("src/test/csv/test.csv");
  }
}
