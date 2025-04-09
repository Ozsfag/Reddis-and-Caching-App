package com.example.demo.configuration.properties;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.cache")
@Data
public class AppCacheProperties {
  private final Map<String, SpecificCacheProperties> caches = new HashMap<>();
}
