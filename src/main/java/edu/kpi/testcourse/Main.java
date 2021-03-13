package edu.kpi.testcourse;

import io.micronaut.runtime.Micronaut;

/**
 * This is a main entry point to the URL shortener.
 *
 * <p>It creates, connects and starts all system parts.
 */
public class Main {
  public static void main(String[] args) {
    Micronaut.run(Main.class, args);
  }
}
