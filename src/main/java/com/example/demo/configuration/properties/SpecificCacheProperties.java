package com.example.demo.configuration.properties;

import java.time.Duration;
import lombok.Data;

@Data
public class SpecificCacheProperties {
  private Duration expiry = Duration.ZERO;
}
