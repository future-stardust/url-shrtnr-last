package edu.kpi.testcourse.logic;

import edu.kpi.testcourse.model.UrlAlias;
import edu.kpi.testcourse.repository.UrlRepository;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Service is used to execute some kind of business logic with urls.
 */
@Singleton
public class UrlService {

  private final UrlRepository urlRepository;

  @Inject
  public UrlService(UrlRepository urlRepository) {
    this.urlRepository = urlRepository;
  }

  public String getOriginUrlByAlias(String alias) {
    return urlRepository.getOriginUrl(alias);
  }

  /**
   * Remove the alias of the specified user.
   *
   * @param alias to be deleted
   * @param email of user who owns the alias
   * @return true if the alias was deleted and
   *         false if alias was not found in list of user's aliases
   */
  public boolean deleteUserUrl(String alias, String email) {
    List<UrlAlias> aliases = urlRepository.getUserUrls(email);
    for (UrlAlias urlAlias : aliases) {
      if (alias.equals(urlAlias.alias())) {
        urlRepository.remove(alias);
        return true;
      }
    }
    return false;
  }
}
