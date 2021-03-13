package edu.kpi.testcourse.logic;

import javax.inject.Singleton;

/**
 * Stupid password encoder that does not encode and returns raw password.
 * Only for the early stage of development, in the future it will be replaced.
 */
@Singleton
public class NoPasswordEncoderImpl implements PasswordEncoder {

  @Override
  public String encode(String rawPassword) {
    return rawPassword;
  }

  @Override
  public boolean matches(String rawPassword, String encodedPassword) {
    return rawPassword.equals(encodedPassword);
  }
}
