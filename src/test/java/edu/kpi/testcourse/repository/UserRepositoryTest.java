package edu.kpi.testcourse.repository;

import static edu.kpi.testcourse.repository.TestUtils.assertFileContentEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.kpi.testcourse.model.User;
import io.micronaut.context.annotation.Property;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.io.File;
import java.util.Optional;
import javax.inject.Inject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
@MicronautTest
class UserRepositoryTest {

  @Inject
  UserRepository userRepository;
  @Property(name = "db.users.filename")
  String fileName;

  @Test
  void test() {
    User user1 = new User("user1@mail.com", "pass123");
    userRepository.save(user1);

    assertFileContentEquals(
      "{\"user1@mail.com\":{\"email\":\"user1@mail.com\",\"password\":\"pass123\"}}",
      fileName
    );
    assertTrue(userRepository.containsUserWithEmail("user1@mail.com"),
      "Repository does not contain user with email=user1@mail.com");
    assertFalse(userRepository.containsUserWithEmail("example@mail.com"),
      "Repository contains user with email=example@mail.com but we did not save it");

    User user2 = new User("user2@mail.com", "123pass");
    userRepository.save(user2);

    assertFileContentEquals(
      "{\"user1@mail.com\":{\"email\":\"user1@mail.com\",\"password\":\"pass123\"},"
        + "\"user2@mail.com\":{\"email\":\"user2@mail.com\",\"password\":\"123pass\"}}",
      fileName
    );
    Optional<User> userOpt1 = userRepository.getUserByEmail("user1@mail.com");
    Optional<User> userOpt2 = userRepository.getUserByEmail("user2@mail.com");
    Optional<User> userOpt3 = userRepository.getUserByEmail("example@mail.com");

    assertTrue(userOpt1.isPresent(), "Repository does not return user");
    assertEquals("user1@mail.com", userOpt1.get().getEmail(), "User email does not match");
    assertEquals("pass123", userOpt1.get().getPassword(), "User password does not match");
    assertTrue(userOpt2.isPresent(), "Repository does not return user");
    assertEquals("user2@mail.com", userOpt2.get().getEmail(), "User email does not match");
    assertEquals("123pass", userOpt2.get().getPassword(), "User password does not match");
    assertFalse(userOpt3.isPresent(), "Repository returns user but we did not save it");
  }

  @AfterAll
  void cleanDatabase() {
    File file = new File(fileName);
    if (!file.delete()) {
      throw new Error("Can not delete file after testing");
    }
  }
}
