package edu.kpi.testcourse.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
class ActiveSessionRepositoryTest {

  @Inject
  ActiveSessionRepository sessionRepository;

  @Test
  void testSaveJwt() {
    sessionRepository.save("abc");
    assertTrue(sessionRepository.contains("abc"));
    sessionRepository.remove("abc");
    assertFalse(sessionRepository.contains("abc"));
  }
}
