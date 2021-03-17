package edu.kpi.testcourse.model;

import java.util.Objects;

/**
 * UrlAlias model which contains origin url, alias for origin url and email of owner.
 */
public final class UrlAlias {

  private final String alias;
  private final String originUrl;
  private final String ownerEmail;

  /**
   * Basic constructor for UrlAlias model.
   *
   * @param alias short version of origin url
   * @param originUrl origin site url
   * @param ownerEmail owner email of alias
   */
  public UrlAlias(String alias, String originUrl, String ownerEmail) {
    this.alias = alias;
    this.originUrl = originUrl;
    this.ownerEmail = ownerEmail;
  }

  public String alias() {
    return alias;
  }

  public String originUrl() {
    return originUrl;
  }

  public String ownerEmail() {
    return ownerEmail;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (UrlAlias) obj;
    return Objects.equals(this.alias, that.alias)
      && Objects.equals(this.originUrl, that.originUrl)
      && Objects.equals(this.ownerEmail, that.ownerEmail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, originUrl, ownerEmail);
  }

  @Override
  public String toString() {
    return "UrlAlias["
      + "alias=" + alias + ", "
      + "originUrl=" + originUrl + ", "
      + "ownerEmail=" + ownerEmail + ']';
  }
}
