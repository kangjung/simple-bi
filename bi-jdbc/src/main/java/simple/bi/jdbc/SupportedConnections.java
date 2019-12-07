package simple.bi.jdbc;

/**
 * Defining supported database.
 */
public enum SupportedConnections {
  MYSQL("mysql"),
  POSTGRESQL("postgresql");

  private String jdbcName;

  SupportedConnections(String jdbcName) {
    this.jdbcName = jdbcName;
  }

  public String getJdbcName() {
    return this.jdbcName;
  }
}
