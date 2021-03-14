package edu.kpi.testcourse.logic;

import io.micronaut.context.annotation.Prototype;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.inject.Inject;

/**
 * Basic password encoder implementation using SHA-256 hashing algorithm.
 */
@Prototype
public class PasswordEncoderImpl implements PasswordEncoder {

  private final MessageDigest messageDigest;

  @Inject
  public PasswordEncoderImpl() throws NoSuchAlgorithmException {
    this.messageDigest = MessageDigest.getInstance("SHA-256");
  }

  @Override
  public String encode(String rawPassword) {
    byte[] hashedPassword = messageDigest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(hashedPassword);
  }

  @Override
  public boolean matches(String rawPassword, String encodedPassword) {
    byte[] rawPasswordBytes = messageDigest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
    return encodedPassword.equals(Base64.getEncoder().encodeToString(rawPasswordBytes));
  }
}
