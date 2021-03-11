package edu.kpi.testcourse.auth;

import edu.kpi.testcourse.repository.ActiveSessionRepository;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * JwtIdGenerator generates id for jwt.
 */
@Singleton
public class JwtIdGenerator implements
    io.micronaut.security.token.jwt.generator.claims.JwtIdGenerator {

  private final ActiveSessionRepository activeSessionRepository;

  @Inject
  public JwtIdGenerator(ActiveSessionRepository activeSessionRepository) {
    this.activeSessionRepository = activeSessionRepository;
  }

  @Override
  public String generateJtiClaim() {
    String jwtId = UUID.randomUUID().toString();
    activeSessionRepository.save(jwtId);
    return jwtId;
  }
}
