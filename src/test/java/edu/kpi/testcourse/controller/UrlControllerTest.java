package edu.kpi.testcourse.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.kpi.testcourse.logic.PasswordEncoder;
import edu.kpi.testcourse.logic.PasswordEncoderImpl;
import edu.kpi.testcourse.model.UrlAlias;
import edu.kpi.testcourse.model.User;
import edu.kpi.testcourse.repository.UrlRepository;
import edu.kpi.testcourse.repository.UrlRepositoryImpl;
import edu.kpi.testcourse.repository.UserRepository;
import edu.kpi.testcourse.repository.UserRepositoryImpl;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
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
  PasswordEncoder passwordEncoder;

  @MockBean(UrlRepositoryImpl.class)
  UrlRepository urlRepo() {
    return mock(UrlRepository.class);
  }

  @MockBean(UserRepositoryImpl.class)
  UserRepository userRepo() {
    return mock(UserRepository.class);
  }

  @MockBean(PasswordEncoderImpl.class)
  PasswordEncoder passwordEncoder() {
    return mock(PasswordEncoder.class);
  }

  @Test
  void testUserSuccessfullyDeletedUrl() {
    when(urlRepository.getUserUrls("email1"))
      .thenReturn(
        List.of(
          new UrlAlias("a", "aaa", "email1"),
          new UrlAlias("b", "bbb", "email1")
        )
      );
    when(userRepository.getUserByEmail("email1")).thenReturn(Optional.of(new User("email1", "123")));
    when(passwordEncoder.matches("123", "123")).thenReturn(true);

    HttpResponse<BearerAccessRefreshToken> response1 = client.toBlocking()
      .exchange(
        HttpRequest.POST("/login", new UsernamePasswordCredentials("email1", "123")),
        BearerAccessRefreshToken.class
      );

    HttpResponse<String> response2 = client.toBlocking()
      .exchange(HttpRequest.DELETE("/urls/a").bearerAuth(response1.body().getAccessToken()), String.class);

    assertEquals(HttpStatus.NO_CONTENT, response2.status());
    verify(urlRepository).remove("a");
  }

  @Test
  void testUserCantDeleteUrl() {
    when(urlRepository.getUserUrls("email1"))
      .thenReturn(
        List.of(
          new UrlAlias("a", "aaa", "email1"),
          new UrlAlias("b", "bbb", "email1")
        )
      );
    when(userRepository.getUserByEmail("email1")).thenReturn(Optional.of(new User("email1", "123")));
    when(passwordEncoder.matches("123", "123")).thenReturn(true);

    HttpResponse<BearerAccessRefreshToken> response1 = client.toBlocking()
      .exchange(
        HttpRequest.POST("/login", new UsernamePasswordCredentials("email1", "123")),
        BearerAccessRefreshToken.class
      );

    Executable e = () -> client.toBlocking()
      .exchange(HttpRequest.DELETE("/urls/c").bearerAuth(response1.body().getAccessToken()), String.class);

    HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, e);
    assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
  }
}
