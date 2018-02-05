package lebed.ecommerce.integration.github;

import lebed.ecommerce.GitIntegrationProperties;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

@Service
public class GithubClient {

    private static final Pattern LINK_PATTERN = Pattern.compile("<(.+)>; rel=\"(.+)\"");
    private static final String HTTPS_API_GITHUB_COM_USERS = "https://api.github.com/users/%s";
    private static final String ECOMMERCE_GITHUB_REQUESTS = "ecommerce.github.requests";
    private static final String ECOMMERCE_GITHUB_FAILURES = "ecommerce.github.failures";

    private final CounterService counterService;

    private final RestTemplate restTemplate;

    public GithubClient(CounterService counterService, RestTemplateBuilder restTemplateBuilder,
                        GitIntegrationProperties properties) {
        this.counterService = counterService;
        this.restTemplate = restTemplateBuilder.additionalCustomizers(rt ->
                rt.getInterceptors().add(new GithubAppTokenInterceptor(properties.getGithub().getToken()))).build();
    }

    @Cacheable("github.user")
    public GithubUser getUser(String githubId) {
        return invoke(createRequestEntity(
                String.format(HTTPS_API_GITHUB_COM_USERS, githubId)), GithubUser.class).getBody();
    }

    private <T> ResponseEntity<T> invoke(RequestEntity<?> request, Class<T> type) {
        counterService.increment(ECOMMERCE_GITHUB_REQUESTS);
        try {
            return restTemplate.exchange(request, type);
        } catch (RestClientException e) {
            counterService.increment(ECOMMERCE_GITHUB_FAILURES);
            throw e;
        }
    }

    private RequestEntity<?> createRequestEntity(String url) {
        try {
            return RequestEntity.get(new URI(url)).accept(MediaType.APPLICATION_JSON).build();
        } catch (URISyntaxException ex) {
            throw new IllegalStateException("Invalid URL " + url, ex);
        }
    }

    private static class GithubAppTokenInterceptor implements ClientHttpRequestInterceptor {

        private final String token;

        GithubAppTokenInterceptor(String token) {
            this.token = token;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
                                            ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
            if (StringUtils.hasText(this.token)) {
                byte[] basicAuthValue = this.token.getBytes(StandardCharsets.UTF_8);
                httpRequest.getHeaders().set(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString(basicAuthValue));
            }
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        }

    }

}
