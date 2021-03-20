package edu.kpi.testcourse.repository;

import static edu.kpi.testcourse.repository.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.kpi.testcourse.model.UrlAlias;
import io.micronaut.context.annotation.Property;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.io.File;
import java.util.List;
import javax.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
@MicronautTest
class UrlRepositoryTest {

  @Inject
  UrlRepository urlRepository;
  @Property(name = "db.urls.filename")
  String fileName;

  @Test
  void test() throws Exception {
    UrlAlias url1 = new UrlAlias("g", "google", "user1");
    UrlAlias url2 = new UrlAlias("f", "facebook", "user1");
    UrlAlias url3 = new UrlAlias("a", "amazon", "user2");
    urlRepository.save(url1);

    assertFileContentEquals(
      "{\"g\":{\"alias\":\"g\",\"originUrl\":\"google\",\"ownerEmail\":\"user1\"}}",
      fileName
    );
    assertEquals("google", urlRepository.getOriginUrl("g"));

    Exception exception = assertThrows(Exception.class, () -> urlRepository.save(url1));
    assertEquals("Alias already exists", exception.getMessage());

    urlRepository.save(url2);
    urlRepository.save(url3);

    Assertions.assertThat(urlRepository.getUserUrls("user1"))
      .hasSameElementsAs(
        List.of(
          new UrlAlias("g", "google", "user1"),
          new UrlAlias("f", "facebook", "user1")
        )
      );

    urlRepository.remove("g");

    assertNull(urlRepository.getOriginUrl("g"));
  }

  @AfterAll
  void cleanDatabase() {
    File file = new File(fileName);
    if (!file.delete()) {
      throw new Error("Can not delete file after testing");
    }
  }
}
