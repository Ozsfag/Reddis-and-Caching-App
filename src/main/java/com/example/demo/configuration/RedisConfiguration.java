package com.example.demo.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ConditionalOnProperty(prefix = "app.redis", name = "enable", havingValue = "true")
public class RedisConfiguration {

  @Bean
  public LettuceConnectionFactory lettuceConnectionFactory(RedisProperties redisProperties) {
    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();

    configuration.setHostName(redisProperties.getHost());
    configuration.setPort(redisProperties.getPort());
    return new LettuceConnectionFactory(configuration);
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(
      LettuceConnectionFactory lettuceConnectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(lettuceConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    return redisTemplate;
  }
}
