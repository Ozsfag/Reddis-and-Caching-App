package com.example.demo.configuration;

import com.example.demo.configuration.properties.AppCacheProperties;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@EnableCaching
public class CacheConfiguration {
  @Bean
  @ConditionalOnProperty(prefix = "app.redis", name = "enabled", havingValue = "true")
  public CacheManager redisManager(
      AppCacheProperties properties, LettuceConnectionFactory factory) {
    var defaultConfig = RedisCacheConfiguration.defaultCacheConfig();
    Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
    properties
        .getCaches()
        .forEach(
            (cacheName, _) -> redisCacheConfigurationMap.put(
                cacheName,
                RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(properties.getCaches().get(cacheName).getExpiry())));

    return RedisCacheManager.builder(factory)
        .cacheDefaults(defaultConfig)
        .withInitialCacheConfigurations(redisCacheConfigurationMap)
        .build();
  }
}
