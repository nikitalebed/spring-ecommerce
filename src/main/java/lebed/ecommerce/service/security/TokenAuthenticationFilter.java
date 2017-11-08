package lebed.ecommerce.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String AUTH_HEADER;

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private UserDetailsService userDetailServiceImpl;

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String error = "";
        String authToken = getToken(request);
        if (authToken != null) {
            String username = tokenHelper.getUsernameFromToken(authToken);
            if (username != null) {
                UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(username);
                TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                authentication.setToken(authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                error = "Username from token can't be found in DB.";
            }
        }
        if (!error.equals("")) {
            SecurityContextHolder.getContext().setAuthentication(new AnonAuthentication());//prevent show login form...
        }
        chain.doFilter(request, response);
    }
}
