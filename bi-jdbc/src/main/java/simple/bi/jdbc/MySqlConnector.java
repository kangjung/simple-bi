package simple.bi.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;

public class MySqlConnector extends AbstractJdbcConnector {
  private String url;
  private String userName;
  private String password;

  public MySqlConnector(String url, String userName, String password) {
    this.url = url;
    this.userName = userName;
    this.password = password;
  }

  protected BasicDataSource getDataSource() {
    BasicDataSource basicDataSource = new BasicDataSource();
    basicDataSource.setMinIdle(AbstractJdbcConnector.DEFAULT_MIN_POOL_SIZE);
    basicDataSource.setMaxIdle(AbstractJdbcConnector.DEFAULT_MIN_POOL_SIZE * 2);
    basicDataSource.setUrl(String.format("jdbc:%s://%s",
      SupportedConnections.MYSQL.getJdbcName(), this.url));
    basicDataSource.setUsername(this.userName);
    basicDataSource.setPassword(this.password);
    return basicDataSource;
  }
}
