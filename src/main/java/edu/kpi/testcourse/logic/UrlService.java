package edu.kpi.testcourse.logic;

import edu.kpi.testcourse.repository.UrlRepository;
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
}
