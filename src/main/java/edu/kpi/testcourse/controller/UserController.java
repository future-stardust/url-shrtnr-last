package edu.kpi.testcourse.controller;

import edu.kpi.testcourse.logic.UserService;
import edu.kpi.testcourse.model.User;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.Authenticator;
import io.micronaut.security.authentication.UserDetails;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.handlers.LoginHandler;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Flowable;
import io.reactivex.Single;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller that handles requests to /users/*.
 */
@Controller("/users")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  protected final Authenticator authenticator;
  protected final LoginHandler loginHandler;
  private final UserService userService;

  /**
   * This constructor is used by the DI to create singleton.
   *
   * @param authenticator collects authentication providers
   *                      ({@link edu.kpi.testcourse.auth.AuthenticationProviderUserPassword
   *                      AuthenticationProviderUserPassword} in our case) that authenticate
   * @param loginHandler handles response if login success or failed
   * @param userService service for work with user
   */
  @Inject
  public UserController(Authenticator authenticator, LoginHandler loginHandler,
      UserService userService) {
    this.authenticator = authenticator;
    this.loginHandler = loginHandler;
    this.userService = userService;
  }

  @Post("/signup")
  @Secured(SecurityRule.IS_ANONYMOUS)
  HttpResponse<?> signUp(@Valid @Body User user) throws Exception {
    logger.info("Processing POST /users/signup request");
    userService.registerUser(user);
    return HttpResponse.status(HttpStatus.CREATED);
  }

  @Post("/signin")
  @Secured(SecurityRule.IS_ANONYMOUS)
  Single<MutableHttpResponse<?>> signIn(@Valid @Body User user, HttpRequest<?> request) {
    logger.info("Processing POST /users/signin request");

    Flowable<AuthenticationResponse> authenticationResponseFlowable = Flowable
        .fromPublisher(authenticator.authenticate(
          request,
          new UsernamePasswordCredentials(user.getEmail(), user.getPassword()))
    );

    return authenticationResponseFlowable.map(authenticationResponse -> {
      if (authenticationResponse.isAuthenticated() && authenticationResponse.getUserDetails()
          .isPresent()) {
        UserDetails userDetails = authenticationResponse.getUserDetails().get();
        return loginHandler.loginSuccess(userDetails, request);
      } else {
        return loginHandler.loginFailed(authenticationResponse, request);
      }
    }).first(HttpResponse.status(HttpStatus.UNAUTHORIZED));
  }
}
