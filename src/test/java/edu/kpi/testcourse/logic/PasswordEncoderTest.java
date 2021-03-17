package edu.kpi.testcourse.logic;

import static org.junit.jupiter.api.Assertions.*;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
class PasswordEncoderTest {

  @Inject
  PasswordEncoder passwordEncoder;

  @Test
  void testPasswordMatchesAfterHashing() {
    String hashedPassword = passwordEncoder.encode("asdfghjkl");

    assertTrue(passwordEncoder.matches("asdfghjkl", hashedPassword));
  }

  @Test
  void testHashedPasswordIsDifferentFromRaw() {
    String hashedPassword = passwordEncoder.encode("asdfghjkl");

    assertNotEquals("asdfghjkl", hashedPassword, "The hashed password must not be the same as the raw password.");
  }
}
