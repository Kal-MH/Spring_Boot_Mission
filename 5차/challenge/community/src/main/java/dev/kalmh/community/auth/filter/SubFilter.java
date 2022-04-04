package dev.kalmh.community.auth.filter;

import dev.kalmh.community.auth.LikelionSsoConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@Component
public class SubFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SubFilter.class);

    //auth 서버로부터 로그인결과가 돌아오면 subfilter가 작동해서 쿠키 확인.
    //- 하지만 현재 쿼리를 통해서 값을 전달하고 있기 때문에 확인할 수 없다.
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies){
                if (cookie.getName().equals(LikelionSsoConsts.LIKELION_LOGIN_COOKIE)){
                    logger.info("Login Token Found, {}", cookie.getValue());
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        logger.info("Login Token Missing");
        chain.doFilter(request, response);

    }
}
