package edu.kpi.testcourse.repository;

import edu.kpi.testcourse.model.User;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple implementation of user storage that uses java serialization to save data in file.
 */
@Singleton
public class UserRepositoryImpl implements UserRepository {

  private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
  private final Map<String, User> users = new ConcurrentHashMap<>();

  @Override
  public void save(User user) {
    logger.debug("Saving user={}", user.toString());
    users.put(user.email(), user);
  }

  @Override
  public boolean containsUserWithEmail(String email) {
    return users.containsKey(email);
  }

  @Override
  public Optional<User> getUserByEmail(String email) {
    return Optional.ofNullable(users.get(email));
  }
}
