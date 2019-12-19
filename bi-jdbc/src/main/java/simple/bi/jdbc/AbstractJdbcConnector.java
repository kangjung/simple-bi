package simple.bi.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractJdbcConnector {

  public static final int DEFAULT_MIN_POOL_SIZE;
  public static final String VALIDATION_QUERY;

  static {
    DEFAULT_MIN_POOL_SIZE = 1;
    VALIDATION_QUERY = "SELECT 1 FROM DUAL";
  }

  private String url;
  private String userName;
  private String password;

  private BasicDataSource basicDataSource;

  protected abstract BasicDataSource getDataSource();

  public AbstractJdbcConnector(String url, String userName, String password) {
    this.url = url;
    this.userName = userName;
    this.password = password;

    this.basicDataSource = this.getDataSource();
  }

  public abstract void testConnection(boolean close) throws SQLException;

  public abstract Map<String, List<Map<String, String>>> getTableMap() throws SQLException;

  protected void close() throws SQLException {
    this.basicDataSource.close();
    this.basicDataSource = null;
  }

  public Connection getConnection() throws SQLException {
    return this.basicDataSource.getConnection();
  }

  public List<Map<String, Object>> runQuery(String sql, int limit) throws SQLException {
    List<Map<String, Object>> queryResult = new ArrayList<>();
    // TODO(kuckjwi): row limit.
    try (
        Connection conn = this.getConnection();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
    ) {
      ResultSetMetaData metaData = rs.getMetaData();
      int columnCount = metaData.getColumnCount();
      while (rs.next()) {
        Map<String, Object> dataMap = new HashMap<>();
        for (int i = 1; i <= columnCount; i++) {
          dataMap.put(metaData.getColumnName(i), rs.getString(i));
        }
        queryResult.add(dataMap);
      }
    }
    return queryResult;
  }

  public String getPassword() {
    return password;
  }

  public String getUserName() {
    return userName;
  }

  public String getUrl() {
    return url;
  }
}
