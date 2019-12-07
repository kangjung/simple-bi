package simple.bi.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractJdbcConnector {
  protected static final int DEFAULT_MIN_POOL_SIZE = 1;

  private BasicDataSource basicDataSource;

  protected abstract BasicDataSource getDataSource();

  protected void init() {
    this.basicDataSource = this.getDataSource();
  }

  protected void close() throws SQLException {
    this.basicDataSource.close();
  }

  public Connection getConnection() throws SQLException {
    return this.basicDataSource.getConnection();
  }
}
