package edu.kpi.testcourse.logic;

/**
 * Encodes password and checks if raw password matches encoded.
 */
public interface PasswordEncoder {
  /**
   * Encodes password using some hashing algorithm.
   *
   * @param rawPassword to be hashed
   * @return hashed password
   */
  String encode(String rawPassword);

  /**
   * Checks if the raw password matches the encoded password.
   *
   * @param rawPassword unencrypted password
   * @param encodedPassword hashed password
   * @return true if the raw password matches the encoded, false otherwise.
   */
  boolean matches(String rawPassword, String encodedPassword);
}
