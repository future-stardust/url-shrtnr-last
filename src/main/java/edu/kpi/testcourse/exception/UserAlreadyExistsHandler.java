package edu.kpi.testcourse.exception;

import edu.kpi.testcourse.model.ErrorResponse;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import javax.inject.Singleton;

/**
 * Handler of {@link UserAlreadyExists} exception.
 */
@Produces
@Singleton
@Requires(classes = {UserAlreadyExists.class, ExceptionHandler.class})
public class UserAlreadyExistsHandler implements
    ExceptionHandler<UserAlreadyExists, HttpResponse<ErrorResponse>> {

  @Override
  public HttpResponse<ErrorResponse> handle(HttpRequest request, UserAlreadyExists exception) {
    return HttpResponse.badRequest(new ErrorResponse(2, exception.getMessage()));
  }
}
