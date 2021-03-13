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
  void test() {
    String hashedPassword = passwordEncoder.encode("asdfghjkl");

    assertTrue(passwordEncoder.matches("asdfghjkl", hashedPassword));
  }
}
