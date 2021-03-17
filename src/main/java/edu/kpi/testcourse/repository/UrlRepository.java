package edu.kpi.testcourse.repository;

import edu.kpi.testcourse.model.UrlAlias;
import java.util.List;

/**
 * Interface for working with URL storage.
 */
public interface UrlRepository {
  /**
   * Stores short url, origin url and email of owner.
   *
   * @param urlAlias that you want to save
   */
  void save(UrlAlias urlAlias) throws Exception;

  /**
   * Returns urls that user has saved.
   *
   * @param email of user whose url you want to get
   * @return user's url list
   */
  List<UrlAlias> getUserUrls(String email);

  /**
   * Deletes info about url with the given alias.
   *
   * @param alias of url you want to delete
   */
  void remove(String alias);

  /**
   * Returns origin URL for alias.
   *
   * @param alias short version of origin url
   * @return origin url for alias
   */
  String getOriginUrl(String alias);
}
