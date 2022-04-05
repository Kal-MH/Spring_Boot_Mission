package dev.kalmh.auth.filter;

import dev.kalmh.auth.entity.model.RedisRepository;
import dev.kalmh.auth.service.PublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SsoLogoutSuccessHandler implements LogoutSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(SsoLogoutSuccessHandler.class);
    private final PublisherService publisherService;
    private final RedisRepository redisRepository;

    public SsoLogoutSuccessHandler(
            PublisherService publisherService,
            RedisRepository redisRepository
    ) {
        this.publisherService = publisherService;
        this.redisRepository = redisRepository;
    }
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();


        String cookieValue = "";
        if (cookies != null) {
            for(Cookie cookie : cookies) {
                if (cookie.getName().equals("likelion_login_cookie")) {
                    cookieValue = cookie.getValue();
                    break;
                }
            }
        }
        logger.info("Logout Success Handler : { }", cookieValue);
        if (!cookieValue.equals("")) {
            this.redisRepository.deleteById(cookieValue);
            publisherService.publishMessage(cookieValue);
        }
        response.sendRedirect("");
    }
}
