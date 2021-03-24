package edu.kpi.testcourse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Information about aliases that the user receives when requesting list of his saved aliases.
 *
 * @param url a origin version of URL
 * @param alias an alias
 * @param shortenedUrl - {base URL shortener URL}/r/{alias}
 */
public record Alias(
    @JsonProperty("url") String url,
    @JsonProperty("alias") String alias,
    @JsonProperty("shortened_url") String shortenedUrl
) {
}
