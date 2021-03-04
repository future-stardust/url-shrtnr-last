package edu.kpi.testcourse;

import com.google.gson.Gson;
import io.micronaut.context.annotation.Factory;
import javax.inject.Singleton;

/**
 * Bean factory provides beans for DI.
 */
@Factory
public class BeanFactory {
  @Singleton
  Gson getGson() {
    return new Gson();
  }
}
