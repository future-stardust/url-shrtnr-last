package edu.kpi.testcourse.repository;

import edu.kpi.testcourse.model.UrlAlias;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Nullable;
import javax.inject.Singleton;

/**
 * A fake implementation of {@link UrlRepository}.
 * Only for the early stage of development, in the future it will be replaced.
 */
@Singleton
public class UrlRepositoryFakeImpl implements UrlRepository {
  private final HashMap<String, UrlAlias> aliases = new HashMap<>();

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
