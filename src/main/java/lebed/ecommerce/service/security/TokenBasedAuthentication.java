package lebed.ecommerce.service.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;


public class TokenBasedAuthentication extends AbstractAuthenticationToken {

    @Getter
    @Setter
    private String token;

    private final UserDetails userDetails;

    public TokenBasedAuthentication(UserDetails userDetails) {
        super(userDetails.getAuthorities());
        this.userDetails = userDetails;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }
}
