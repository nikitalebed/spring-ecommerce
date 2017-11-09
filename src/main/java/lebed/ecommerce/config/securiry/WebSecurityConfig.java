package lebed.ecommerce.config.securiry;

import lebed.ecommerce.service.security.AuthenticationFailureHandler;
import lebed.ecommerce.service.security.AuthenticationSuccessHandler;
import lebed.ecommerce.service.security.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtAuthenticationTokenFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/product/image/**").permitAll()
                .antMatchers(HttpMethod.GET, "/product/**").permitAll()
                .antMatchers(HttpMethod.GET, "/group/**").permitAll()
                .antMatchers("/cart/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/index.html").permitAll()
                .anyRequest().authenticated()
                .anyRequest().hasAuthority("admin")
                .and().formLogin().successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and().csrf().disable();
    }
        // TODO uncomment it after finishing ui part
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public TokenAuthenticationFilter jwtAuthenticationTokenFilter() throws Exception {
        return new TokenAuthenticationFilter();
    }
}
