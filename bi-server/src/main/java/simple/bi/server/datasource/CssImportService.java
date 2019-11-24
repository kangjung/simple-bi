package simple.bi.server.datasource;

import java.io.IOException;

public interface CssImportService {
  /**
   * CSV file import.
   *
   * @param filePath file path
   * @throws IOException In case of an error.
   */
  void importCsv(String filePath) throws IOException;


  /**
   * Detect csv separator.
   *
   * @return csv separator
   */
  char detectSeparator();
}
