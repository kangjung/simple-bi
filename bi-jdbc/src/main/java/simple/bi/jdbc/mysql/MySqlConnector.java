package simple.bi.jdbc.mysql;

import org.apache.commons.dbcp2.BasicDataSource;
import simple.bi.jdbc.AbstractJdbcConnector;
import simple.bi.jdbc.SupportedConnections;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
    return basicDataSource;
  }

  @Override
  public void testConnection() throws SQLException {
    try (
      Connection conn = super.getConnection();
      Statement statement = conn.createStatement();
    ) {
      statement.execute(AbstractJdbcConnector.VALIDATION_QUERY);
      super.close();
    }
  }
}
