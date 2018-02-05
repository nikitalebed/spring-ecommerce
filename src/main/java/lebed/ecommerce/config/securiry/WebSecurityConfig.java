package lebed.ecommerce.config.securiry;

import lebed.ecommerce.GitIntegrationProperties;
import lebed.ecommerce.integration.github.GithubClient;
import lebed.ecommerce.integration.github.GithubUser;
import lebed.ecommerce.model.User;
import lebed.ecommerce.repository.UserRepository;
import org.apache.catalina.security.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final GitIntegrationProperties gitIntegrationProperties;

    public WebSecurityConfig(GitIntegrationProperties GitIntegrationProperties) {
        this.gitIntegrationProperties = GitIntegrationProperties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/", "/news", "/login**", "/css/**", "/img/**", "/webjars/**", "/bootstrap/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .headers()
                .frameOptions().sameOrigin();
    }


    @Bean
    public AuthoritiesExtractor authoritiesExtractor() {
        return map -> {
            String username = (String) map.get("login");
            if (this.gitIntegrationProperties.getSecurity().getAdmins().contains(username)) {
                return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_ADMIN");
            } else {
                return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
            }
        };
    }

    @Bean
    public PrincipalExtractor principalExtractor(GithubClient githubClient, UserRepository userRepository) {
        return map -> {
            String githubLogin = (String) map.get("login");
            User user = userRepository.findByGithub(githubLogin);
            if (user == null) {
                logger.info("Initialize user with githubId {}", githubLogin);
                GithubUser gitUser = githubClient.getUser(githubLogin);
                user = new User();
                user.setEmail(gitUser.getEmail());
                user.setName(gitUser.getName());
                user.setGithub(githubLogin);
                user.setAvatarUrl(gitUser.getAvatar());
                userRepository.save(user);
            }
            return user;
        };

    }
}
