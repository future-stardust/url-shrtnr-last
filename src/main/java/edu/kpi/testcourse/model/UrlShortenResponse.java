package edu.kpi.testcourse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Successful result of URL shortening.
 */
public record UrlShortenResponse(@JsonProperty("shortened_url") String shortenedUrl) { }
