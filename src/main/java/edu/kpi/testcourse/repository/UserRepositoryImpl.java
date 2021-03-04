package edu.kpi.testcourse.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.kpi.testcourse.model.User;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple implementation of user storage that uses gson to save data in file.
 */
@Singleton
public class UserRepositoryImpl implements UserRepository {

  private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
  private final Map<String, User> users;
  private final Gson gson;
  private final String fileName = "users.txt";

  public UserRepositoryImpl() {
    this.gson = new Gson();
    this.users = initFileStorage();
  }

  @Override
  public void save(User user) {
    logger.debug("Saving user={}", user.toString());
    users.put(user.getEmail(), user);
    writeUsersToFile();
  }

  @Override
  public boolean containsUserWithEmail(String email) {
    return users.containsKey(email);
  }

  @Override
  public Optional<User> getUserByEmail(String email) {
    return Optional.ofNullable(users.get(email));
  }

  private void writeUsersToFile() {
    try (FileWriter fileWriter = new FileWriter(fileName)) {
      fileWriter.write(gson.toJson(users));
    } catch (IOException e) {
      logger.error("Can not write users to file");
      throw new Error("Error while writing users to file");
    }
  }

  private Map<String, User> readUsersFromFile() {
    File file = new File(fileName);
    Type type = new TypeToken<Map<String, User>>(){}.getType();
    try {
      String json = Files.readString(file.toPath());
      return gson.fromJson(json, type);
    } catch (IOException e) {
      logger.error("Can not read users from file");
      throw new Error("Error while reading users from file");
    }
  }

  private Map<String, User> initFileStorage() {
    File file = new File(fileName);
    try {
      if (file.createNewFile()) {
        logger.info("File for user storage has been created");
        return new ConcurrentHashMap<>();
      } else {
        return new ConcurrentHashMap<>(readUsersFromFile());
      }
    } catch (IOException e) {
      logger.error("Can not create file for user storage", e);
      throw new Error("Error while creating file for user storage");
    }
  }
}
