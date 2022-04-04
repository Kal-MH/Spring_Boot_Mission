package dev.kalmh.auth.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
//SavedRequestAwareAuthenticationSuccessHandler : 마지막으로 들어오는 요청을 저장.
public class SsoCookieHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final int COOKIE_EXPIRY = 30 * 24 * 60 * 60;

    //로그인 성공 후에 호출되는 함수
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        logger.info("onAuthenticationSuccess, create new cookie");

        Cookie loginCookie = new Cookie(
                "likelion_login_cookie",
                "test_value"
        );
        logger.debug("set cookie max age");
        loginCookie.setMaxAge(COOKIE_EXPIRY);
        loginCookie.setPath("/"); //쿠키가 어느 경로에서 조회가 가능한지
        logger.info("set cookie to httpServletResponse");
        response.addCookie(loginCookie);

        logger.debug("call super");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
