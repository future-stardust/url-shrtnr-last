package edu.kpi.testcourse.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import edu.kpi.testcourse.logic.PasswordEncoder;
import edu.kpi.testcourse.model.User;
import edu.kpi.testcourse.repository.UserRepository;
import edu.kpi.testcourse.repository.UserRepositoryImpl;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.reactivex.subscribers.TestSubscriber;
import java.util.Optional;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;

@MicronautTest
class AuthenticationProviderUserPasswordTest {

  @Inject
  AuthenticationProvider authenticationProvider;
  @Inject
  UserRepository userRepository;
  @Inject
  PasswordEncoder passwordEncoder;

  @MockBean(UserRepositoryImpl.class)
  UserRepository userRepo() {
    return mock(UserRepository.class);
  }

  @Test
  void testAuthenticateSuccessful() {
    when(userRepository.getUserByEmail("example@mail.com"))
      .thenReturn(Optional.of(new User("example@mail.com", passwordEncoder.encode("pass123"))));

    AuthenticationRequest<String, String> authenticationRequest = mock(AuthenticationRequest.class);
    when(authenticationRequest.getIdentity()).thenReturn("example@mail.com");
    when(authenticationRequest.getSecret()).thenReturn("pass123");

    Publisher<AuthenticationResponse> auth = authenticationProvider
      .authenticate(null, authenticationRequest);

    TestSubscriber<AuthenticationResponse> subscriber = new TestSubscriber<>();
    auth.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValue(authenticationResponse -> {
      assertTrue(authenticationResponse.isAuthenticated());
      assertEquals("example@mail.com", authenticationResponse.getUserDetails().get().getUsername());
      return true;
    });
  }

  @Test
  void testAuthenticateFailedNoUserWithEmail() {
    when(userRepository.getUserByEmail("example@mail.com"))
      .thenReturn(Optional.of(new User("example@mail.com", passwordEncoder.encode("pass123"))));

    AuthenticationRequest<String, String> authenticationRequest = mock(AuthenticationRequest.class);
    when(authenticationRequest.getIdentity()).thenReturn("user@mail.com");
    when(authenticationRequest.getSecret()).thenReturn("pass123");

    userRepository.getUserByEmail("examp");
    userRepository.getUserByEmail("example@mail.com");

    Publisher<AuthenticationResponse> auth = authenticationProvider
      .authenticate(null, authenticationRequest);

    TestSubscriber<AuthenticationResponse> subscriber = new TestSubscriber<>();
    auth.subscribe(subscriber);

    subscriber.assertError(AuthenticationException.class);
    subscriber.assertNotComplete();
  }

  @Test
  void testAuthenticateFailedIncorrectPassword() {
    when(userRepository.getUserByEmail("example@mail.com"))
      .thenReturn(Optional.of(new User("example@mail.com", passwordEncoder.encode("pass123"))));

    AuthenticationRequest<String, String> authenticationRequest = mock(AuthenticationRequest.class);
    when(authenticationRequest.getIdentity()).thenReturn("example@mail.com");
    when(authenticationRequest.getSecret()).thenReturn("pass");

    Publisher<AuthenticationResponse> auth = authenticationProvider
      .authenticate(null, authenticationRequest);

    TestSubscriber<AuthenticationResponse> subscriber = new TestSubscriber<>();
    auth.subscribe(subscriber);

    subscriber.assertError(AuthenticationException.class);
    subscriber.assertNotComplete();
  }
}
