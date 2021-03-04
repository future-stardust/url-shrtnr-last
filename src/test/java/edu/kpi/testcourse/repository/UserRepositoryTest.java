package edu.kpi.testcourse.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.kpi.testcourse.model.User;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.util.Optional;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
class UserRepositoryTest {

  @Inject
  private UserRepository userRepository;

  @Test
  void testContainsUserWithEmail() {
    User user = new User("user1@mail.com", "pass123");
    userRepository.save(user);

    assertTrue(
      userRepository.containsUserWithEmail("user1@mail.com"),
      "Repository does not contain user with email=user1@mail.com"
    );
    assertFalse(
      userRepository.containsUserWithEmail("example@mail.com"),
      "Repository contains user with email=example@mail.com but we did not save it"
    );
  }

  @Test
  void testGetUserByEmail() {
    User user = new User("user2@mail.com", "123pass");
    userRepository.save(user);

    Optional<User> user1 = userRepository.getUserByEmail("user2@mail.com");
    Optional<User> user2 = userRepository.getUserByEmail("example@mail.com");

    assertTrue(
      user1.isPresent(),
      "Repository does not return user"
    );
    assertFalse(
      user2.isPresent(),
      "Repository returns user but we did not save it"
    );
    assertEquals(user, user1.get(), "User data does not match");
  }
}
