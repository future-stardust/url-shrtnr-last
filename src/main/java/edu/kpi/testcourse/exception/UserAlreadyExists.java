package edu.kpi.testcourse.exception;

/**
 * Exception that occurs when the user already exists in the system.
 */
public class UserAlreadyExists extends RuntimeException {
  public UserAlreadyExists() {
    super("Email is already used");
  }
}
