package edu.kpi.testcourse.auth;

import edu.kpi.testcourse.logic.PasswordEncoder;
import edu.kpi.testcourse.model.User;
import edu.kpi.testcourse.repository.UserRepository;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UserDetails;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.reactivestreams.Publisher;

/**
 * Micronaut authentication bean that contains authorization logic: ensures that a user is
 * registered in the system and password is right.
 */
@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Inject
  public AuthenticationProviderUserPassword(UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Publisher<AuthenticationResponse> authenticate(
      @Nullable HttpRequest<?> httpRequest,
      AuthenticationRequest<?, ?> authenticationRequest
  ) {
    return Flowable.create(emitter -> {
      Optional<User> user = userRepository
          .getUserByEmail((String) authenticationRequest.getIdentity());
      if (user.isPresent() && passwordEncoder
          .matches((String) authenticationRequest.getSecret(), user.get().getPassword())) {
        emitter
          .onNext(new UserDetails((String) authenticationRequest.getIdentity(), new ArrayList<>()));
        emitter.onComplete();
      } else {
        emitter.onError(
            new AuthenticationException(new AuthenticationFailed("Wrong username or password"))
        );
      }
    }, BackpressureStrategy.ERROR);
  }
}
