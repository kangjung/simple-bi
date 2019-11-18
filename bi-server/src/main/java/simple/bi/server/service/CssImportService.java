package simple.bi.server.service;

import java.io.IOException;

public interface CssImportService {
  /**
   * CSV file import.
   *
   * @throws IOException In case of an error.
   */
  void importCSV() throws IOException;

  /**
   * Detect csv separator.
   *
   * @return csv separator
   */
  char detectSeparator();
}
