package edu.kpi.testcourse.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestUtils {
  static void assertFileContentEquals(String expected, String fileName) {
    String actual = "";
    try {
      actual = Files.readString(Path.of(fileName));
    } catch (IOException e) {
      throw new Error("Error while reading file");
    }
    assertEquals(expected, actual, "File content of storage does not match with expected");
  }
}
