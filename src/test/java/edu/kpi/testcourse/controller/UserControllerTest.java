package edu.kpi.testcourse.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.kpi.testcourse.model.User;
import edu.kpi.testcourse.repository.ActiveSessionRepository;
import edu.kpi.testcourse.repository.ActiveSessionRepositoryImpl;
import edu.kpi.testcourse.repository.UserRepository;
import edu.kpi.testcourse.repository.UserRepositoryImpl;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.util.Optional;
import javax.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

@MicronautTest
class UserControllerTest {

  @Inject
  @Client("/")
  RxHttpClient client;

  @Inject
  UserRepository userRepository;

  @Inject
  ActiveSessionRepository activeSessionRepository;

  @MockBean(UserRepositoryImpl.class)
  UserRepository userRepo() {
    return mock(UserRepository.class);
  }

  @MockBean(ActiveSessionRepositoryImpl.class)
  ActiveSessionRepository sessionRepo() {
    return mock(ActiveSessionRepository.class);
  }

  @Test
  void testUserLoginSuccessful() {
    when(userRepository.getUserByEmail("user1@mail.com")).thenReturn(Optional.of(new User("user1@mail.com", "pass123")));

    HttpResponse<String> response = client.toBlocking()
      .exchange(HttpRequest.POST("/users/signin", new User("user1@mail.com", "pass123")), String.class);

    assertEquals(response.status(), HttpStatus.OK);
    Assertions.assertThat(response.body()).matches("\\{\"access_token\":\"[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+\"}");
    verify(activeSessionRepository).save(anyString());
  }

  @Test
  void testUserLoginFailed() {
    when(userRepository.getUserByEmail("user1@mail.com")).thenReturn(Optional.of(new User("user1@mail.com", "pass123")));

    Executable e = () -> client.toBlocking()
      .exchange(HttpRequest.POST("/users/signin", new User("us@mail.com", "pass123")), String.class);

    HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, e);
    assertEquals(thrown.getStatus(), HttpStatus.UNAUTHORIZED);
    verify(activeSessionRepository, never()).save(anyString());
  }

  @Test
  void testUserRegisterSuccessful() {
    when(userRepository.containsUserWithEmail("user1@mail.com")).thenReturn(false);

    HttpResponse<String> response = client.toBlocking()
      .exchange(HttpRequest.POST("/users/signup", new User("user1@mail.com", "pass123")), String.class);

    assertEquals(response.status(), HttpStatus.CREATED);
  }

  @Test
  void testUserRegisterFailed() {
    when(userRepository.containsUserWithEmail("user1@mail.com")).thenReturn(true);
    User user = new User("user1@mail.com", "pass123");

    Executable e = () -> client.toBlocking()
      .exchange(HttpRequest.POST("/users/signup", user), String.class);

    HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, e);
    assertEquals(thrown.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
    verify(userRepository, never()).save(user);
  }
}
