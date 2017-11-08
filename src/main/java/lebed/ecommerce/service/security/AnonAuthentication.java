package lebed.ecommerce.service.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AnonAuthentication extends AbstractAuthenticationToken {

    public AnonAuthentication() {
        super(null);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public int hashCode() {
        return 7;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj != null && getClass() == obj.getClass();
    }
}
