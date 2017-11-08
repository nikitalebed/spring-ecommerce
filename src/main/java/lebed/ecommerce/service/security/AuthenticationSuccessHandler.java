package lebed.ecommerce.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${jwt.expires_in}")
    private int EXPIRES_IN;

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {

        clearAuthenticationAttributes(request);
        User user = (User) authentication.getPrincipal();
        String jwt = tokenHelper.generateToken(user.getUsername());

        UserTokenState userTokenState = new UserTokenState(jwt, EXPIRES_IN);
        try {
            String jwtResponse = objectMapper.writeValueAsString(userTokenState);
            response.setContentType("application/json");
            response.getWriter().write(jwtResponse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Data
    @AllArgsConstructor
    private class UserTokenState {
        private String jws;
        private int expires;
    }
}
