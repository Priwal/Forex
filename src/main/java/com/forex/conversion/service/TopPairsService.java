package com.forex.conversion.service;

import com.forex.conversion.entity.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


@Service
@Configuration
public class TopPairsService {
    @Value("localhost")
    private String redisHost;

    @Value("6379")
    private int redisPort;

    @Autowired
    RedisTemplate<String, Object> template;

    @Cacheable(cacheNames = "count", key = "{#currencyPair}")
    public String getTopN(int n) {

        String result = "";
        Set<byte[]> keys = redisConnectionFactory().getConnection().keys("*".getBytes());

        Iterator<byte[]> it = keys.iterator();

        int k = Math.min(n, keys.size());

        while (k-- > 0) {

            byte[] data = (byte[]) it.next();

            result += new String(data) + " ";
        }
        return result;
    }

    public void incrementPairCount(CurrencyPair currencyPair) {
        template.opsForValue().increment("count" + "::" + currencyPair);
    }

    @Bean
    public CacheManager cacheManager(
            RedisConnectionFactory connectionFactory
    ) {
        RedisCacheConfiguration defaultCacheConfig =
                RedisCacheConfiguration.defaultCacheConfig();
        defaultCacheConfig.disableCachingNullValues();
        Map<String, RedisCacheConfiguration> cacheConfigurations =
                new HashMap<>();

        cacheConfigurations.put(
                "count", defaultCacheConfig.
                        entryTtl(Duration.ofHours(1)).
                        serializeValuesWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string())
                        )
        );
        return RedisCacheManager.builder(connectionFactory).
                cacheDefaults(defaultCacheConfig).
                withInitialCacheConfigurations(cacheConfigurations).
                build();
    }

    @Bean
    LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort));
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setDefaultSerializer(new StringRedisSerializer());
        return template;
    }

}
