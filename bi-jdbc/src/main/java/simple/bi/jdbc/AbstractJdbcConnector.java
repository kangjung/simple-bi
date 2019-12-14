package simple.bi.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

// TODO(kuckjwi): lifeCycle.
public abstract class AbstractJdbcConnector {

  public static final int DEFAULT_MIN_POOL_SIZE;
  public static final String VALIDATION_QUERY;

  static {
    DEFAULT_MIN_POOL_SIZE = 1;
    VALIDATION_QUERY = "SELECT 1 FROM DUAL";
  }

  private long lastUsedTimeStamp;

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
    this.lastUsedTimeStamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
  }

  public abstract void testConnection() throws SQLException;

  protected void close() throws SQLException {
    this.basicDataSource.close();
    this.basicDataSource = null;
  }

  public Connection getConnection() throws SQLException {
    this.lastUsedTimeStamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    return this.basicDataSource.getConnection();
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

  public long getLastUsedTimeStamp() {
    return lastUsedTimeStamp;
  }
}
