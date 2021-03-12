package edu.kpi.testcourse.auth;

import com.nimbusds.jwt.JWTClaimsSet.Builder;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.security.authentication.UserDetails;
import io.micronaut.security.token.config.TokenConfiguration;
import io.micronaut.security.token.jwt.generator.claims.ClaimsAudienceProvider;
import io.micronaut.security.token.jwt.generator.claims.JWTClaimsSetGenerator;
import io.micronaut.security.token.jwt.generator.claims.JwtIdGenerator;
import javax.inject.Singleton;

/**
 * CustomJwtClaimsSetGenerator is used to remove unnecessary info from jwt.
 */
@Singleton
@Replaces(bean = JWTClaimsSetGenerator.class)
public class CustomJwtClaimsSetGenerator extends JWTClaimsSetGenerator {

  public CustomJwtClaimsSetGenerator(TokenConfiguration tokenConfiguration,
      @Nullable JwtIdGenerator jwtIdGenerator,
      @Nullable ClaimsAudienceProvider claimsAudienceProvider,
      @Nullable ApplicationConfiguration applicationConfiguration) {
    super(tokenConfiguration, jwtIdGenerator, claimsAudienceProvider, applicationConfiguration);
  }

  @Override
  protected void populateIss(Builder builder) {
  }

  @Override
  protected void populateExp(Builder builder, @Nullable Integer expiration) {
  }

  @Override
  protected void populateNbf(Builder builder) {
  }

  @Override
  protected void populateIat(Builder builder) {
  }

  @Override
  protected void populateWithUserDetails(Builder builder, UserDetails userDetails) {
    builder.subject(userDetails.getUsername());
  }
}
