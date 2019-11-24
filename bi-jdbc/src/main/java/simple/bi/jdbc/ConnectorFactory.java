package simple.bi.jdbc;

public class ConnectorFactory {
  public static AbstractJdbcConnector createConnector(SupportedConnections connectionType) {
    switch (connectionType) {
      case MYSQL:
        return null;
      default:
        throw new IllegalArgumentException("Unknown Connection Type");
    }
  }
}
