package simple.bi.jdbc.mysql;

import org.apache.commons.dbcp2.BasicDataSource;
import simple.bi.jdbc.AbstractJdbcConnector;
import simple.bi.jdbc.SupportedConnections;

import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlConnector extends AbstractJdbcConnector {
  public MySqlConnector(String url, String userName, String password) {
    super(url, userName, password);
  }

  @Override
  protected BasicDataSource getDataSource() {
    BasicDataSource basicDataSource = new BasicDataSource();
    basicDataSource.setMinIdle(AbstractJdbcConnector.DEFAULT_MIN_POOL_SIZE);
    basicDataSource.setMaxIdle(AbstractJdbcConnector.DEFAULT_MIN_POOL_SIZE * 5);
    basicDataSource.setUrl(String.format("jdbc:%s://%s",
      SupportedConnections.MYSQL.getJdbcName(), super.getUrl()));
    basicDataSource.setUsername(super.getUserName());
    basicDataSource.setPassword(super.getPassword());
    // 30 minutes.
    basicDataSource.setMaxConnLifetimeMillis(30 * 60000);
    return basicDataSource;
  }

  @Override
  public void testConnection(boolean close) throws SQLException {
    try (
      Connection conn = super.getConnection();
      Statement statement = conn.createStatement();
    ) {
      statement.execute(AbstractJdbcConnector.VALIDATION_QUERY);
      if (close) {
        super.close();
      }
    }
  }

  @Override
  public Map<String, List<Map<String, String>>> getTableMap() throws SQLException {
    Map<String, List<Map<String, String>>> tableMap = new HashMap<>();
    try (
      Connection conn = this.getConnection();
      ResultSet tableRs = conn.getMetaData()
        .getTables(null, null, "%", new String[]{ "TABLE" })) {
      while (tableRs.next()) {
        String tableName = tableRs.getString("TABLE_NAME");
        List<Map<String, String>> columnList = new ArrayList<>();
        try (ResultSet columnsRs = conn.getMetaData()
          .getColumns(null, null, tableName, null)) {
          while (columnsRs.next()) {
            Map<String, String> columnMap = new HashMap<>();
            columnMap.put("columnType", JDBCType.valueOf(columnsRs.getInt("DATA_TYPE")).toString());
            columnMap.put("columnName", columnsRs.getString("COLUMN_NAME").toUpperCase());
            columnList.add(columnMap);
          }
        }
        tableMap.put(tableName, columnList);
      }
    }
    return tableMap;
  }
}
