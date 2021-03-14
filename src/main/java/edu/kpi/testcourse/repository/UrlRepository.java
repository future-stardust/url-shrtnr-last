package edu.kpi.testcourse.repository;

import edu.kpi.testcourse.model.UrlAlias;

import javax.annotation.Nullable;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Interface for working with URL storage.
 */
public interface UrlRepository {
  /**
   * Stores short url, origin url and email of owner.
   *
   * @param urlAlias that you want to save
   */
  void save(UrlAlias urlAlias);

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

  /**
   * Stores the given URL alias in the repository if it does not already exist.
   *
   * @param urlAlias an pair of full and shortened URLs
   * @throws AliasAlreadyExist if the repository already contains a URL alias with this short name.
   */
  void createUrlAlias(UrlAlias urlAlias) throws AliasAlreadyExist;

  /**
   * Returns complete information about the URL alias with the given short name.
   */
  @Nullable
  UrlAlias findUrlAlias(String alias);

  /**
   * Error for a case when we try to create a shortened URL that is already exist.
   */
  class AliasAlreadyExist extends IllegalStateException {
    public AliasAlreadyExist() {
      super("Storage already contains an alias");
    }
  }
}
