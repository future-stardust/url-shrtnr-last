package edu.kpi.testcourse.repository;

/**
 * Interface for working with session storage.
 */
public interface ActiveSessionRepository {

  void save(String jwtId);

  boolean contains(String jwtId);

  void remove(String jwtId);
}
