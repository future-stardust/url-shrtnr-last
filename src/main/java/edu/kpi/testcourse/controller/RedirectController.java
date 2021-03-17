package edu.kpi.testcourse.controller;

import edu.kpi.testcourse.logic.UrlService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import java.net.URI;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller that handles requests to /r/*.
 */
@Controller("/r")
public class RedirectController {

  private static final Logger logger = LoggerFactory.getLogger(RedirectController.class);
  private final UrlService urlService;

  @Inject
  public RedirectController(UrlService urlService) {
    this.urlService = urlService;
  }

  @Get("/{alias}")
  @Secured(SecurityRule.IS_ANONYMOUS)
  HttpResponse<?> redirectToOriginUrl(@PathVariable String alias) {
    logger.info("Processing GET /r/{} request", alias);
    String originUrl = urlService.getOriginUrlByAlias(alias);
    return originUrl == null
      ? HttpResponse.notFound("no url corresponds to given alias")
      : HttpResponse.redirect(URI.create(originUrl));
  }
}
