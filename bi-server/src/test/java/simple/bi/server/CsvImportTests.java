package simple.bi.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import simple.bi.server.csv.CsvImportServiceImpl;

import java.io.IOException;

@SpringBootTest
public class CsvImportTests {
  @Autowired
  private CsvImportServiceImpl cssImportService;

  @Test
  void csvImportTest() throws IOException {
    cssImportService.importCsv("test_01","src/test/csv/test.csv");
  }
}
