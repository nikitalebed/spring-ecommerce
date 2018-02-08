package lebed.ecommerce;

import lebed.ecommerce.integration.github.GitIntegrationProperties;
import lebed.ecommerce.service.files.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

@SpringBootApplication
@EnableConfigurationProperties(GitIntegrationProperties.class)
@EnableCaching
public class ECommerceStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceStarterApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> storageService.init();
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
