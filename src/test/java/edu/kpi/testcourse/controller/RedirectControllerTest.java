package edu.kpi.testcourse.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import edu.kpi.testcourse.repository.UrlRepository;
import edu.kpi.testcourse.repository.UrlRepositoryImpl;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

@MicronautTest
class RedirectControllerTest {

  @Inject
  @Client("/")
  RxHttpClient client;

  @Inject
  UrlRepository urlRepository;

  @MockBean(UrlRepositoryImpl.class)
  UrlRepository urlRepo() {
    return mock(UrlRepository.class);
  }

  @Test
  void testRedirectedSuccessfully() {
    when(urlRepository.getOriginUrl("k")).thenReturn("https://kpi.ua/");

    HttpResponse<String> response = client.toBlocking()
      .exchange(HttpRequest.GET("/r/k"), String.class);

    assertEquals(HttpStatus.OK, response.status());
  }

  @Test
  void testRedirectFailed() {
    when(urlRepository.getOriginUrl("g")).thenReturn(null);

    Executable e = () -> client.toBlocking()
      .exchange(HttpRequest.GET("/r/g"), String.class);

    HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, e);
    assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    assertEquals("no url corresponds to given alias", thrown.getResponse().body());
  }
}
