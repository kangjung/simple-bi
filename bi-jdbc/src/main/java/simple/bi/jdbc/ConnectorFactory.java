package simple.bi.jdbc;

import simple.bi.jdbc.mysql.MySqlConnector;

public class ConnectorFactory {
  /**
   * Connector create.
   *
   * @param params
   * [0] - url
   * [1] - userName
   * [2] - password
   * [3] - dbType
   * @return JdbcConnector
   */
  public static AbstractJdbcConnector create(String... params) {
    return switch (SupportedConnections.valueOf(params[3].toUpperCase())) {
      case MYSQL -> new MySqlConnector(params[0], params[1], params[2]);
      case POSTGRESQL -> null;
    };
  }
}
