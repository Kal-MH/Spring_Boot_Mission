package dev.kalmh.community.infra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final static Logger logger = LoggerFactory.getLogger(CustomAuthFailureHandler.class);
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        logger.info(exception + "");
        String errorMsg = "";
        if (exception instanceof UsernameNotFoundException)
            errorMsg = "Login Fail : Username Not Found.";
        else if (exception instanceof BadCredentialsException)
            errorMsg = "Login Fail : Invalid id, password";
       setDefaultFailureUrl("/user/login?error=true&exception=" + errorMsg);
        super.onAuthenticationFailure(request, response, exception);
    }
}