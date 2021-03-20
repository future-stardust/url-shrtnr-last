package edu.kpi.testcourse.controller;

import edu.kpi.testcourse.logic.UrlService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import java.security.Principal;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller that handles requests to /urls/*.
 */
@Controller("/urls")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class UrlController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private final UrlService urlService;

  @Inject
  public UrlController(UrlService urlService) {
    this.urlService = urlService;
  }

  @Delete("/{alias}")
  HttpResponse<?> deleteUrl(@PathVariable String alias, Principal principal) {
    logger.info("Processing DELETE /urls/{} request", alias);
    return urlService.deleteUserUrl(alias, principal.getName())
      ? HttpResponse.noContent()
      : HttpResponse.notFound();
  }
}
