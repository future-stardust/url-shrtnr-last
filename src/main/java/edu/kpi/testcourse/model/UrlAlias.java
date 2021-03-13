package edu.kpi.testcourse.model;

/**
 * UrlAlias model which contains origin url, alias for origin url and email of owner.
 */
public record UrlAlias(String alias, String originUrl, String ownerEmail) {}
