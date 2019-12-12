package simple.bi.server.csvimport;

import java.io.IOException;

public interface CsvImportService {
  /**
   * Csv file import.
   *
   * @param tableName table name.
   * @param filePath file path.
   * @throws IOException In case of an error.
   * @throws IOException In case of an sql error.
   */
  void importCsv(String tableName, String filePath) throws IOException;
}
