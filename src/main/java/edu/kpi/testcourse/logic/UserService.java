package edu.kpi.testcourse.logic;

import edu.kpi.testcourse.exception.UserAlreadyExists;
import edu.kpi.testcourse.model.User;
import edu.kpi.testcourse.repository.UserRepository;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Service is used to execute some kind of business logic with users.
 */
@Singleton
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Inject
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Performs user registration.
   *
   * @param user who wants to register
   */
  public void registerUser(User user) {
    if (userRepository.containsUserWithEmail(user.getEmail())) {
      throw new UserAlreadyExists();
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }
}
