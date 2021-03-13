package edu.kpi.testcourse.model;

import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * User model which contains info about user.
 */
@Introspected
public class User {

  @NotBlank
  @NotNull
  private String email;

  @NotBlank
  @NotNull
  private String password;

  public User() {}

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "User{"
      + "email='" + email + '\''
      + ", password='" + password + '\''
      + '}';
  }
}
