package edu.kpi.testcourse.controller;

import edu.kpi.testcourse.logic.UserService;
import edu.kpi.testcourse.model.User;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
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
  private final UserService userService;

  /**
   * This constructor is used by the DI to create singleton.
   *
   * @param userService service for work with user
   */
  @Inject
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Post("/signup")
  @Secured(SecurityRule.IS_ANONYMOUS)
  HttpResponse<?> signUp(@Valid @Body User user) throws Exception {
    logger.info("Processing POST /users/signup request");
    userService.registerUser(user);
    return HttpResponse.status(HttpStatus.CREATED);
  }
}
