package edu.kpi.testcourse.repository;

import static edu.kpi.testcourse.repository.TestUtils.assertFileContentEquals;
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
  void test() {
    sessionRepository.save("abc");
    sessionRepository.save("xyz");

    assertFileContentEquals("[\"abc\",\"xyz\"]", fileName);
    assertTrue(sessionRepository.contains("abc"), "Storage must contain id that we saved");
    assertTrue(sessionRepository.contains("xyz"), "Storage must contain id that we saved");
    assertFalse(sessionRepository.contains("qwe"), "Storage must not contain id that we did not save");

    sessionRepository.remove("xyz");
    assertFileContentEquals("[\"abc\"]", fileName);
    assertTrue(sessionRepository.contains("abc"), "Storage must contain id that we saved");
    assertFalse(sessionRepository.contains("xyz"), "Storage must not contain id that we removed");
  }

  @AfterAll
  void cleanDatabase() {
    File file = new File(fileName);
    if (!file.delete()) throw new Error("Can not delete file after testing");
  }
}
