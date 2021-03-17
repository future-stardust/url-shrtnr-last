package edu.kpi.testcourse.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.kpi.testcourse.model.UrlAlias;
import io.micronaut.context.annotation.Property;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple implementation of url storage that uses gson to save data in file.
 */
@Singleton
public class UrlRepositoryImpl implements UrlRepository {
  private final HashMap<String, UrlAlias> aliases = new HashMap<>();

  private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
  private final Map<String, UrlAlias> urls;
  private final Gson gson;
  private final File file;

  /**
   * This constructor is used by the DI to create singleton.
   *
   * @param gson class for serializing and deserializing data
   * @param fileName file name for storing urls
   */
  @Inject
  public UrlRepositoryImpl(Gson gson, @Property(name = "db.urls.filename") String fileName) {
    this.gson = gson;
    this.file = new File(fileName);
    this.urls = initFileStorage();
  }

  @Override
  public void save(UrlAlias urlAlias) throws Exception {
    if (urls.containsKey(urlAlias.alias())) {
      throw new Exception("Alias already exists");
    }
    urls.put(urlAlias.alias(), urlAlias);
    writeUrlsToFile();
  }

  @Override
  public List<UrlAlias> getUserUrls(String email) {
    return urls
      .values()
      .stream()
      .filter(urlAlias -> urlAlias.ownerEmail().equals(email))
      .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public void remove(String alias) {
    urls.remove(alias);
    writeUrlsToFile();
  }

  @Override
  public String getOriginUrl(String alias) {
    UrlAlias urlAlias = urls.get(alias);
    if (urlAlias == null) {
      throw new Error("Alias does not exist");
    }
    return urlAlias.originUrl();
  }

  private void writeUrlsToFile() {
    try (FileWriter fileWriter = new FileWriter(file)) {
      fileWriter.write(gson.toJson(urls));
    } catch (IOException e) {
      logger.error("Can not write urls to file");
      throw new Error("Error while writing urls to file");
    }
  }

  private Map<String, UrlAlias> initFileStorage() {
    try {
      if (file.createNewFile()) {
        logger.info("File for url storage has been created");
        return new ConcurrentHashMap<>();
      } else {
        return new ConcurrentHashMap<>(readUrlsFromFile());
      }
    } catch (IOException e) {
      logger.error("Can not create file for url storage", e);
      throw new Error("Error while creating file for url storage");
    }
  }

  private Map<String, UrlAlias> readUrlsFromFile() {
    Type type = new TypeToken<Map<String, UrlAlias>>(){}.getType();
    try {
      String json = Files.readString(file.toPath());
      Map<String, UrlAlias> urls = gson.fromJson(json, type);
      return urls == null ? new HashMap<>() : urls;
    } catch (IOException e) {
      logger.error("Can not read urls from file");
      throw new Error("Error while reading urls from file");
    }
  }

  @Override
  public void createUrlAlias(UrlAlias urlAlias) {
    if (aliases.containsKey(urlAlias.alias())) {
      throw new UrlRepository.AliasAlreadyExist();
    }

    aliases.put(urlAlias.alias(), urlAlias);
  }

  @Override
  public @Nullable
  UrlAlias findUrlAlias(String alias) {
    return aliases.get(alias);
  }
}
