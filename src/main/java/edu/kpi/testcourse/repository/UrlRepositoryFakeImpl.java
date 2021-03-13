package edu.kpi.testcourse.repository;

import edu.kpi.testcourse.model.UrlAlias;
import java.util.List;
import javax.inject.Singleton;

/**
 * A fake implementation of {@link UrlRepository}.
 * Only for the early stage of development, in the future it will be replaced.
 */
@Singleton
public class UrlRepositoryFakeImpl implements UrlRepository {

  @Override
  public void save(UrlAlias urlAlias) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<UrlAlias> getUserUrls(String email) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void remove(String alias) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getOriginUrl(String alias) {
    throw new UnsupportedOperationException();
  }
}
