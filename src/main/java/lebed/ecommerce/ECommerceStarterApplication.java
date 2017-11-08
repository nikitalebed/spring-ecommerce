package lebed.ecommerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import lebed.ecommerce.service.cache.Cache;
import lebed.ecommerce.service.cache.RedisCache;
import lebed.ecommerce.service.files.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;

@SpringBootApplication
public class ECommerceStarterApplication {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Value("${redis.password}")
    private String redisPassword;

    public static void main(String[] args) {
        SpringApplication.run(ECommerceStarterApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> storageService.init();
    }

    @Bean
    @Autowired
    public Cache cacheObject(ObjectMapper objectMapper) {
        Jedis jedis = new Jedis(redisHost, redisPort);
        jedis.auth(redisPassword);
        return new RedisCache(objectMapper, jedis);
    }
}
