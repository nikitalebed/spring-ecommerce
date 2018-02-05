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
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

@SpringBootApplication
@EnableConfigurationProperties(GitIntegrationProperties.class)
@EnableCaching
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

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cacheManager -> cacheManager.createCache("github.user", initConfiguration(Duration.ONE_HOUR));
    }

    private MutableConfiguration<Object, Object> initConfiguration(Duration duration) {
        return new MutableConfiguration<>()
                .setStoreByValue(false)
                .setStatisticsEnabled(true)
                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(duration));
    }
}
