package lebed.ecommerce.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lebed.ecommerce.service.cache.Cache;
import lebed.ecommerce.service.cache.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Value("${redis.password}")
    private String redisPassword;

    @Bean
    @Autowired
    public Cache cacheObject(ObjectMapper objectMapper) {
        Jedis jedis = new Jedis(redisHost, redisPort);
//        jedis.auth(redisPassword);
        return new RedisCache(objectMapper, jedis);
    }

}
