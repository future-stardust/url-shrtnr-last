package edu.kpi.testcourse.repository;

import edu.kpi.testcourse.model.User;
import java.util.Optional;

/**
 * Interface for working with user storage.
 */
public interface UserRepository {

  void save(User user);

  boolean containsUserWithEmail(String email);

  Optional<User> getUserByEmail(String email);
}
