package edu.kpi.testcourse.repository;

import java.util.LinkedList;
import java.util.List;
import javax.inject.Singleton;

/**
 * Simple implementation of session storage.
 */
@Singleton
public class ActiveSessionRepositoryImpl implements ActiveSessionRepository {

  private static final List<String> sessions = new LinkedList<>();

  @Override
  public void save(String jwtId) {
    sessions.add(jwtId);
  }

  @Override
  public boolean contains(String jwtId) {
    return sessions.contains(jwtId);
  }

  @Override
  public void remove(String jwtId) {
    sessions.remove(jwtId);
  }
}
