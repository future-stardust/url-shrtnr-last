package edu.kpi.testcourse.logic;

import edu.kpi.testcourse.model.UrlAlias;
import edu.kpi.testcourse.model.User;
import edu.kpi.testcourse.repository.UrlRepository;
import edu.kpi.testcourse.repository.UrlRepository.AliasAlreadyExist;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UrlService {

  private final UrlRepository urlRepository;
  private final UrlRepository urls;

  @Inject
  public UrlService(UrlRepository urlRepository,  UrlRepository urls) {
    this.urlRepository = urlRepository;
    this.urls = urls;
  }

  /**
   * Performs user registration.
   *
   * @param email email of who wants to get urls
   */
  public void getLinks(String email) throws Exception {
    urlRepository.getUserUrls(email);
  }

  /**
   * Create a new URL alias (shortened version).
   *
   * @param email an email of a user that creates the alias
   * @param url a full URL
   * @param alias a proposed alias
   *
   * @return a shortened URL
   */
  public String createNewAlias(String email, String url, String alias) throws AliasAlreadyExist {
    String finalAlias;
    if (alias == null || alias.isEmpty()) {
      // TODO: Generate short alias
      throw new UnsupportedOperationException("Is not implemented yet");
    } else {
      finalAlias = alias;
    }

    urls.createUrlAlias(new UrlAlias(finalAlias, url, email));

    return finalAlias;
  }
}
