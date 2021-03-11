package edu.kpi.testcourse.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.micronaut.context.annotation.Property;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple implementation of session storage that uses gson to save data in file.
 */
@Singleton
public class ActiveSessionRepositoryImpl implements ActiveSessionRepository {

  private static final Logger logger = LoggerFactory.getLogger(ActiveSessionRepositoryImpl.class);
  private final List<String> sessions;
  private final Gson gson;
  private final File file;

  /**
   * This constructor is used by the DI to create singleton.
   *
   * @param gson class for serializing and deserializing data
   * @param fileName file name for storing sessions
   */
  @Inject
  public ActiveSessionRepositoryImpl(Gson gson,
      @Property(name = "db.sessions.filename") String fileName) {
    this.gson = gson;
    this.file = new File(fileName);
    this.sessions = initFileStorage();
  }

  @Override
  public void save(String jwtId) {
    logger.debug("Saving jwtId={}", jwtId);
    sessions.add(jwtId);
    writeSessionsToFile();
  }

  @Override
  public boolean contains(String jwtId) {
    return sessions.contains(jwtId);
  }

  @Override
  public void remove(String jwtId) {
    logger.debug("Deleting jwtId={}", jwtId);
    sessions.remove(jwtId);
    writeSessionsToFile();
  }

  private void writeSessionsToFile() {
    try (FileWriter fileWriter = new FileWriter(file)) {
      fileWriter.write(gson.toJson(sessions));
    } catch (IOException e) {
      logger.error("Can not write sessions to file");
      throw new Error("Error while writing sessions to file");
    }
  }

  private List<String> initFileStorage() {
    try {
      if (file.createNewFile()) {
        logger.info("File for session storage has been created");
        return new LinkedList<>();
      } else {
        return new LinkedList<>(readSessionsFromFile());
      }
    } catch (IOException e) {
      logger.error("Can not create file for session storage", e);
      throw new Error("Error while creating file for session storage");
    }
  }

  private List<String> readSessionsFromFile() {
    Type type = new TypeToken<List<String>>(){}.getType();
    try {
      String json = Files.readString(file.toPath());
      List<String> sessions = gson.fromJson(json, type);
      return sessions == null ? new LinkedList<>() : sessions;
    } catch (IOException e) {
      logger.error("Can not read sessions from file");
      throw new Error("Error while reading sessions from file");
    }
  }
}
