package edu.kpi.testcourse.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.kpi.testcourse.model.ErrorResponse;
import edu.kpi.testcourse.model.User;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

@MicronautTest
class UserControllerTest {

  @Inject
  @Client("/")
  RxHttpClient client;

  @Inject
  UserRepository userRepository;

  @MockBean(UserRepositoryImpl.class)
  UserRepository userRepo() {
    return mock(UserRepository.class);
  }

  @Test
  void testUserRegisterSuccessful() {
    when(userRepository.containsUserWithEmail("user1@mail.com")).thenReturn(false);

    HttpResponse<String> response = client.toBlocking()
      .exchange(HttpRequest.POST("/users/signup", new User("user1@mail.com", "pass123")), String.class);

    assertEquals(HttpStatus.CREATED, response.status());
  }

  @Test
  void testUserRegisterFailed() {
    when(userRepository.containsUserWithEmail("user1@mail.com")).thenReturn(true);
    User user = new User("user1@mail.com", "pass123");

    Executable e = () -> client.toBlocking()
      .exchange(HttpRequest.POST("/users/signup", user), String.class);

    HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, e);
    assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
    Optional<ErrorResponse> responseBody = thrown.getResponse().getBody(ErrorResponse.class);
    assertTrue(responseBody.isPresent(), "The response body must not be null");
    assertEquals(2, responseBody.get().reasonCode());
    assertEquals("Email is already used", responseBody.get().reasonText());
    verify(userRepository, never()).save(user);
  }
}
