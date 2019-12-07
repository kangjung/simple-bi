package simple.bi.jdbc;

public class ConnectorFactory {
  public static AbstractJdbcConnector create(SupportedConnections connectionType) {
    return switch (connectionType) {
      case MYSQL -> new MySqlConnector("", "", "");
      case POSTGRESQL -> null;
    };
  }
}
