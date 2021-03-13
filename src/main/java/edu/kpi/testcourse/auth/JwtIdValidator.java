package edu.kpi.testcourse.auth;

import com.nimbusds.jwt.JWTClaimsSet;
import edu.kpi.testcourse.repository.ActiveSessionRepository;
import io.micronaut.security.token.jwt.generator.claims.JwtClaims;
import io.micronaut.security.token.jwt.validator.GenericJwtClaimsValidator;
import io.micronaut.security.token.jwt.validator.JWTClaimsSetUtils;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JwtIdValidator verifies that jti from jwt is still active.
 */
@Singleton
public class JwtIdValidator implements GenericJwtClaimsValidator {
  private static final Logger logger = LoggerFactory.getLogger(JwtIdValidator.class);

  private final ActiveSessionRepository activeSessionRepository;

  @Inject
  JwtIdValidator(ActiveSessionRepository activeSessionRepository) {
    this.activeSessionRepository = activeSessionRepository;
  }

  @Override
  public boolean validate(JwtClaims claims) {
    return validate(JWTClaimsSetUtils.jwtClaimsSetFromClaims(claims));
  }

  private boolean validate(JWTClaimsSet jwtClaimsSetFromClaims) {
    String jwtId = jwtClaimsSetFromClaims.getJWTID();
    return activeSessionRepository.contains(jwtId);
  }
}
