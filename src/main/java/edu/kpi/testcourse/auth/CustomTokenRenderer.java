package edu.kpi.testcourse.auth;

import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.security.authentication.UserDetails;
import io.micronaut.security.token.jwt.render.AccessRefreshToken;
import io.micronaut.security.token.jwt.render.BearerTokenRenderer;
import io.micronaut.security.token.jwt.render.TokenRenderer;
import javax.inject.Singleton;

/**
 * CustomTokenRenderer removes unnecessary fields from response(we need only token).
 */
@Singleton
@Replaces(bean = BearerTokenRenderer.class)
public class CustomTokenRenderer implements TokenRenderer {

  @Override
  public AccessRefreshToken render(Integer expiresIn,
      String accessToken, @Nullable String refreshToken) {
    return new AccessRefreshToken(accessToken, null, null);
  }

  @Override
  public AccessRefreshToken render(UserDetails userDetails,
      Integer expiresIn, String accessToken, @Nullable String refreshToken) {
    return new AccessRefreshToken(accessToken, null, null);
  }
}
