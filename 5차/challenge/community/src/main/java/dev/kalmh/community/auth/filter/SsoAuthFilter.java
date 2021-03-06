package dev.kalmh.community.auth.filter;

import dev.kalmh.community.auth.LikelionSsoConsts;
import dev.kalmh.community.controller.dto.UserInfoDto;
import dev.kalmh.community.service.webclient.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
public class SsoAuthFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SsoAuthFilter.class);
    private final AuthService authService;

    public SsoAuthFilter(AuthService authService) {this.authService = authService;}
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Optional<String> authToken = authTokenFromCookie(httpRequest.getCookies());
        if (authToken.isEmpty()) {
            authToken = authTokenFromQuery(httpRequest, httpResponse);
        }

        if (authToken.isPresent()) {
            logger.info("Login Token Value: {}", authToken.get());
            this.setAuthentication(httpRequest, httpResponse, authToken.get());
        } else {
            logger.info("Login Token Missing");
            this.setAnonymousAuthentication();
        }
        chain.doFilter(request, response);
    }

    private Optional<String> authTokenFromCookie(Cookie[] cookies){
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(LikelionSsoConsts.LIKELION_LOGIN_COOKIE)) {
                    logger.debug("found token in cookie");
                    return Optional.of(cookie.getValue());
                }
            }
        }
        logger.debug("could not find token from cookie");
        return Optional.empty();
    }

    private Optional<String> authTokenFromQuery(
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ) throws IOException {
        String queryString = httpRequest.getQueryString();
        if (queryString == null) {
            logger.debug("query string is null");
            return Optional.empty();
        }
        String[] queryParams = queryString.split("&");
        for (String queryParam: queryParams) {
            String[] queryParamSplit = queryParam.split("=");
            if (queryParamSplit.length == 1) continue;
            if (queryParamSplit[0].equals(LikelionSsoConsts.LIKELION_LOGIN_COOKIE)) {
                logger.debug("found token in query");
                String loginToken = queryParamSplit[1];
                Cookie newTokenCookie = new Cookie(LikelionSsoConsts.LIKELION_LOGIN_COOKIE, loginToken);
                newTokenCookie.setPath("/");
                httpResponse.addCookie(newTokenCookie);
                return Optional.of(queryParamSplit[1]);
            }
        }
        logger.debug("could not find token from query");
        return Optional.empty();
    }

    private void setAuthentication(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String tokenValue){
        // 1. request userInfo with WebClient
        UserInfoDto userInfoDto = this.authService.getUserInfo(tokenValue);
        // 2. set Authentication based on token
        if (userInfoDto != null) {
            logger.info("Login UserInfo: {}", userInfoDto.getUsername());
            this.setLoginAuthentication();
        }
        else {
            logger.info("Anonymous");
            this.setAnonymousAuthentication();
        }

    }

    public void setAnonymousAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(
                    new AnonymousAuthenticationToken(
                            "anonymous",
                            "anonymous",
                            Collections.singletonList(
                                    (GrantedAuthority) () -> "ROLE_ANONYMOUS"))
            );
    }
    public void setLoginAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.emptyList();
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return (Principal) () -> "dummy";
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "dummy";
            }
        });
    }
    private void setSsoAuthentication(String s) {
        SecurityContextHolder.getContext().setAuthentication(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.emptyList();
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return (Principal) () -> "dummy";
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "dummy";
            }
        });
    }
}
