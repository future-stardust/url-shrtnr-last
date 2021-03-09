package edu.kpi.testcourse.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.micronaut.context.annotation.Property;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.io.File;
import javax.inject.Inject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
@MicronautTest
class ActiveSessionRepositoryTest {

  @Inject
  ActiveSessionRepository sessionRepository;
  @Property(name = "db.sessions.filename")
  String fileName;

  @Test
  void testSaveJwt() {
    sessionRepository.save("abc");
    assertTrue(sessionRepository.contains("abc"));
    sessionRepository.remove("abc");
    assertFalse(sessionRepository.contains("abc"));
  }

  @AfterAll
  void cleanDatabase() {
    File file = new File(fileName);
    if (!file.delete()) throw new Error("Can not delete file after testing");
  }
}
