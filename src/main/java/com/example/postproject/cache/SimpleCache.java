package com.example.postproject.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;


@SuppressWarnings({"checkstyle:MissingJavadocType", "checkstyle:Indentation"})
@Component
public class SimpleCache {
  @SuppressWarnings("checkstyle:Indentation")
  private final Map<String, Object> cache = new HashMap<>();

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public void put(String key, Object value) {
        cache.put(key, value);
    }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public Optional<Object> get(String key) {
        return Optional.ofNullable(cache.get(key));
    }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public void remove(String key) {
        cache.remove(key);
    }

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public void clear() {
        cache.clear();
    }
}