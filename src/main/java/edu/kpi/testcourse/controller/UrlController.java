package edu.kpi.testcourse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.kpi.testcourse.logic.UrlService;
import edu.kpi.testcourse.model.ErrorResponse;
import edu.kpi.testcourse.model.UrlShortenRequest;
import edu.kpi.testcourse.model.UrlShortenResponse;
import edu.kpi.testcourse.serialization.JsonTool;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.server.util.HttpHostResolver;
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
  private final HttpHostResolver httpHostResolver;
  private final JsonTool json;
  private final UrlService urlService;

  /**
   * This constructor is used by the DI to create singleton.
   *
   * @param urlService service for work with url
   * @param httpHostResolver micronaut httpHostResolver
   * @param json JSON serialization tool
   */
  @Inject
  public UrlController(UrlService urlService, HttpHostResolver httpHostResolver, JsonTool json) {
    this.urlService = urlService;
    this.httpHostResolver = httpHostResolver;
    this.json = json;
  }

  @Delete("/{alias}")
  HttpResponse<?> deleteUrl(@PathVariable String alias, Principal principal) {
    logger.info("Processing DELETE /urls/{} request", alias);
    return urlService.deleteUserUrl(alias, principal.getName())
      ? HttpResponse.noContent()
      : HttpResponse.notFound();
  }

  /**
   * Create alias for URL.
   */
  @Post(value = "/shorten", processes = MediaType.APPLICATION_JSON)
  public HttpResponse<String> shorten(
      @Body UrlShortenRequest request,
      Principal principal,
      HttpRequest<?> httpRequest
  ) throws JsonProcessingException {
    String email = principal.getName();
    try {
      String baseUrl = httpHostResolver.resolve(httpRequest);
      var shortenedUrl = baseUrl + "/r/"
          + urlService.createNewAlias(email, request.url(), request.alias());
      return HttpResponse.created(
        json.toJson(new UrlShortenResponse(shortenedUrl)));
    } catch (Exception e) {
      return HttpResponse.serverError(
        json.toJson(new ErrorResponse(1, "Alias is already taken"))
      );
    }
  }

  @Get
  HttpResponse<?> getUserUrls(Principal principal, HttpRequest<?> httpRequest) {
    logger.info("Processing GET /urls request");
    String baseUrl = httpHostResolver.resolve(httpRequest);
    return HttpResponse.ok(urlService.getUserUrls(principal.getName(), baseUrl));
  }
}
