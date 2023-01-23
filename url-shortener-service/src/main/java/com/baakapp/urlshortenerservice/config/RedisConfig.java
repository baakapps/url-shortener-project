package com.baakapp.urlshortenerservice.config;

import com.baakapp.urlshortenerservice.entity.Url;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    public RedisTemplate<Long, Url> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Long, Url> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
