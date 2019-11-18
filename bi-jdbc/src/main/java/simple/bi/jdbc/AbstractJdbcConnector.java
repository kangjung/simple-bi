package simple.bi.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractJdbcConnector {
  public static final int DEFAULT_MIN_POOL_SIZE = 5;

  private BasicDataSource basicDataSource;

  public AbstractJdbcConnector() {
    this.basicDataSource = this.getDataSource();
  }

  protected abstract BasicDataSource getDataSource();

  public Connection getConnection() throws SQLException {
    return this.basicDataSource.getConnection();
  }

  public void close() throws SQLException {
    if (this.basicDataSource != null) {
      this.basicDataSource.close();
    }
  }
}
