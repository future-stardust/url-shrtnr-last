package edu.kpi.testcourse.model;

import java.io.Serializable;

/**
 * User model which contains info about user.
 */
public record User(String email, String password) implements Serializable {
}
