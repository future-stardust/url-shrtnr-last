package edu.kpi.testcourse.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.kpi.testcourse.model.UrlAlias;
import edu.kpi.testcourse.model.User;
import edu.kpi.testcourse.repository.ActiveSessionRepository;
import edu.kpi.testcourse.repository.ActiveSessionRepositoryImpl;
import edu.kpi.testcourse.repository.UrlRepository;
import edu.kpi.testcourse.repository.UserRepository;
import edu.kpi.testcourse.repository.UrlRepositoryFakeImpl;
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
class UrlControllerTest {

  @Inject
  @Client("/")
  RxHttpClient client;

  @Inject
  UrlRepository urlRepository;

  @Inject
  UserRepository userRepository;

  @Inject
  ActiveSessionRepository activeSessionRepository;

  @MockBean(UrlRepositoryFakeImpl.class)
  UrlRepositoryFakeImpl urlRepo() {
    return mock(UrlRepositoryFakeImpl.class);
  }

  @MockBean(ActiveSessionRepositoryImpl.class)
  ActiveSessionRepository sessionRepo() {
    return mock(ActiveSessionRepository.class);
  }

//  @Test
//  void testUrlListUnSuccessful() {
//
//    HttpResponse<String> response = client.toBlocking()
//      .exchange(HttpRequest.GET("/urls"));
//    assertEquals(response.status(), HttpStatus.UNAUTHORIZED);
//  }

//  @Test
//  void testUserLoginFailed() {
//    when(userRepository.getUserByEmail("user1@mail.com")).thenReturn(Optional.of(new User("user1@mail.com", "pass123")));
//
//    Executable e = () -> client.toBlocking()
//      .exchange(HttpRequest.POST("/users/signin", new User("us@mail.com", "pass123")), String.class);
//
//    HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, e);
//    assertEquals(thrown.getStatus(), HttpStatus.UNAUTHORIZED);
//    verify(activeSessionRepository, never()).save(anyString());
//  }


}
