package edu.kpi.testcourse.controller;

import edu.kpi.testcourse.model.User;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller that handles requests to /users/*.
 */
@Controller("/users")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Post("/signin")
  @Secured(SecurityRule.IS_ANONYMOUS)
  HttpResponse<?> signIn(@Valid @Body User user) {
    logger.debug("Processing POST /users/signin request {}", user);
    return HttpResponse.ok();
  }
}
