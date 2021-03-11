package edu.kpi.testcourse.auth;

import java.util.UUID;
import javax.inject.Singleton;

/**
 * JwtIdGenerator generates id for jwt.
 */
@Singleton
public class JwtIdGenerator implements
    io.micronaut.security.token.jwt.generator.claims.JwtIdGenerator {

  @Override
  public String generateJtiClaim() {
    return UUID.randomUUID().toString();
  }
}
