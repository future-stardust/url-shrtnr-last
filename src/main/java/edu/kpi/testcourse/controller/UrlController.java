package edu.kpi.testcourse.controller;

import edu.kpi.testcourse.logic.UrlService;
import edu.kpi.testcourse.logic.UserService;
import edu.kpi.testcourse.model.UrlAlias;
import edu.kpi.testcourse.model.User;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
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

import java.security.Principal;


/**
 * Controller that handles requests to /users/*.
 */
@Controller("/urls")
public class UrlController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private final UrlService urlService;

  /**
   * This constructor is used by the DI to create singleton.
   *
   * @param urlService service for work with user
   */
  @Inject
  public UrlController(UrlService urlService) {
    this.urlService = urlService;
  }

  @Get
  HttpResponse<?> getLinks(Principal principal) throws Exception {

    logger.info("Processing GET /urls request");
    urlService.getLinks(principal.getName());
    return HttpResponse.status(HttpStatus.OK);
  }


}
