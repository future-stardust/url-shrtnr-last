package edu.kpi.testcourse.logic;

import edu.kpi.testcourse.model.Alias;
import edu.kpi.testcourse.model.UrlAlias;
import edu.kpi.testcourse.model.Urls;
import edu.kpi.testcourse.repository.UrlRepository;
import java.util.List;
import java.util.stream.Collectors;
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
   * Create a new URL alias (shortened version).
   *
   * @param email an email of a user that creates the alias
   * @param url   a full URL
   * @param alias a proposed alias
   * @return a shortened URL
   */
  public String createNewAlias(String email, String url, String alias) throws Exception {
    String finalAlias;
    if (alias == null || alias.isEmpty()) {
      // TODO: Generate short alias
      throw new UnsupportedOperationException("Is not implemented yet");
    } else {
      finalAlias = alias;
    }

    urlRepository.save(new UrlAlias(finalAlias, url, email));

    return finalAlias;
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

  /**
   * Returns list of aliases belonging to the user with the given email.
   *
   * @param email email of user that wants to see his saved aliases
   * @param hostName URL of shortener
   * @return list of urls
   */
  public Urls getUserUrls(String email, String hostName) {
    return new Urls(
      urlRepository.getUserUrls(email)
        .stream()
        .map(urlAlias -> new Alias(urlAlias.originUrl(), urlAlias.alias(),
          hostName + "/r/" + urlAlias.alias()))
        .collect(Collectors.toUnmodifiableList())
    );
  }
}
