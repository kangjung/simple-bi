package simple.bi.server.service;
/**
 * Implement lambda as needed.
 */
@FunctionalInterface
public interface Validator {
  /**
   * Validate.
   *
   * @throws IllegalArgumentException if not valid.
   */
  void checkValid() throws IllegalArgumentException;
}
