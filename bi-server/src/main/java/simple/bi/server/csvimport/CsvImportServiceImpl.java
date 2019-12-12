package simple.bi.server.csvimport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
public class CsvImportServiceImpl implements CsvImportService {

  @Autowired
  private DataSource dataSource;

  @Override
  public void importCsv(String tableName, String filePath) throws IOException {
    this.createTableAndInsertData(tableName, Files.lines(Paths.get(filePath)));
  }

  private void createTableAndInsertData(String tableName, Stream<String> lines) {
    StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
      .append(tableName)
      .append(" (");
    AtomicInteger counter = new AtomicInteger(0);
    String separator = this.detectSeparator();

    Map<Integer, String> typeMap = new HashMap<Integer, String>();

    lines.forEach((data) -> {
      int count = counter.getAndIncrement();

      // e.g. $COL_NAME %s
      if (count == 0) {
        String[] cols = data.split(separator);
        for (int i = 0; i < cols.length; i++) {
          if (i != cols.length - 1) {
            builder.append(cols[i]).append(' ')
              .append("%s").append(',').append(' ');
          } else {
            builder.append(cols[i]).append(' ')
              .append("%s");
          }
        }
        return;
      }

      // e.g. $COL_NAME VARCHAR
      if (count == 1) {
        String temp = builder.toString();
        String[] rowArray = this.getTypeIncludeRows(data, separator);
        builder.setLength(0);
        builder.append(String.format(temp, rowArray));
        builder.append(");");
        for (int i = 0; i < rowArray.length; i++) {
          typeMap.put(i, rowArray[i]);
        }
        try (
            Connection conn = this.dataSource.getConnection();
            Statement stmt = conn.createStatement();
        ) {
          // table create
          stmt.execute(builder.toString());
          builder.setLength(0);
        } catch (SQLException se) {
          throw new RuntimeException(se);
        }
      }

      String[] rows = data.split(separator);

      builder.append("INSERT INTO ")
        .append(tableName)
        .append(" VALUES (");

      for (int i = 0; i < rows.length; i++) {
        if (typeMap.get(i).equals(TypeRegex.INT.toString())) {
          builder.append(rows[i]);
        } else {
          builder.append("'").append(rows[i]).append("'");
        }
        if (i + 1 != rows.length) {
          builder.append(',');
        }
      }

      builder.append(");\n");

      // TODO(kuckjwi): add batch.
    });
  }

  private String[] getTypeIncludeRows(String data, String separator) {
    String[] rows = data.split(separator);
    String[] rowArray = new String[rows.length];
    for (int i = 0; i < rows.length; i++) {
      rowArray[i] = this.getH2DatabaseType(rows[i]);
    }
    return rowArray;
  }

  private String detectSeparator() {
    return ",";
  }

  private String getH2DatabaseType(String data) {
    TypeRegex[] regex = TypeRegex.values();
    String h2dbType = "VARCHAR";
    for (TypeRegex r : regex) {
      if (r.isTypeMatches(data)) {
        h2dbType = r.toString();
        break;
      }
    }
    return h2dbType;
  }

  enum TypeRegex {
    INT(Pattern.compile("^[0-9]+$"));

    private Pattern pattern;

    TypeRegex(Pattern p) {
      this.pattern = p;
    }

    boolean isTypeMatches(String input) {
      return this.pattern.matcher(input).matches();
    }
  }
}
