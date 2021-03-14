package edu.kpi.testcourse.logic;

import edu.kpi.testcourse.model.UrlAlias;
import edu.kpi.testcourse.model.User;
import edu.kpi.testcourse.repository.UrlRepository;
import edu.kpi.testcourse.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UrlService {

  private final UrlRepository urlRepository;

  @Inject
  public UrlService(UrlRepository urlRepository) {
    this.urlRepository = urlRepository;
  }

  /**
   * Performs user registration.
   *
   * @param email email of who wants to get urls
   */
  public void getLinks(String email) throws Exception {
    urlRepository.getUserUrls(email);
  }
}
