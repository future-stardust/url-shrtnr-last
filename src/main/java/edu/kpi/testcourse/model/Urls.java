package edu.kpi.testcourse.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Response with a list of URLs.
 */
public record Urls(@JsonProperty("urls") List<Alias> urls) {
}
