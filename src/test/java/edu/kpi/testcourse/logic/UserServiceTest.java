package edu.kpi.testcourse.logic;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.kpi.testcourse.exception.UserAlreadyExists;
import edu.kpi.testcourse.model.User;
import edu.kpi.testcourse.repository.UserRepository;
import edu.kpi.testcourse.repository.UserRepositoryImpl;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

@MicronautTest
class UserServiceTest {

  @Inject
  UserService userService;

  @Inject
  UserRepository userRepository;

  @MockBean(UserRepositoryImpl.class)
  UserRepository userRepo() {
    return mock(UserRepository.class);
  }

  @Test
  void registerUserSuccessful() throws Exception {
    User user = new User("user", "pass");
    when(userRepository.containsUserWithEmail("user")).thenReturn(false);

    userService.registerUser(user);

    verify(userRepository).save(user);
  }

  @Test
  void registerUserFailed() {
    User user = new User("user", "pass");
    when(userRepository.containsUserWithEmail("user")).thenReturn(true);

    Executable e = () -> userService.registerUser(user);

    UserAlreadyExists exception = assertThrows(UserAlreadyExists.class, e);
    assertThat(exception.getMessage()).contains("Email is already used");
    verify(userRepository, never()).save(user);
  }
}
