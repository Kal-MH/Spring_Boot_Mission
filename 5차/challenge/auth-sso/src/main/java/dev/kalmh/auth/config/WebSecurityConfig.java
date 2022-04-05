package dev.kalmh.auth.config;

import dev.kalmh.auth.entity.model.RedisRepository;
import dev.kalmh.auth.filter.SsoCookieHandler;
import dev.kalmh.auth.filter.SsoLogoutHandler;
import dev.kalmh.auth.filter.SsoLogoutSuccessHandler;
import dev.kalmh.auth.infra.CustomUserDetailsService;
import dev.kalmh.auth.infra.NaverOAuth2Service;
import dev.kalmh.auth.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final NaverOAuth2Service oAuth2UserService;
    private final SsoCookieHandler ssoCookieHandler;
    private final SsoLogoutHandler ssoLogoutHandler;


    public WebSecurityConfig(
            @Autowired CustomUserDetailsService customUserDetailsService,
            @Autowired NaverOAuth2Service oAuth2UserService,
            @Autowired SsoCookieHandler ssoCookieHandler,
            @Autowired SsoLogoutHandler ssoLogoutHandler
    ){
        this.userDetailsService = customUserDetailsService;
        this.oAuth2UserService = oAuth2UserService;
        this.ssoCookieHandler = ssoCookieHandler;
        this.ssoLogoutHandler = ssoLogoutHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        "/home/**",
                        "/user/signup/**",
                        "/request-userinfo/**",
                        "/",
                        "/css/**",
                        "/images/**",
                        "/js/**"
                )
                .permitAll()
                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/home")
                .successHandler(ssoCookieHandler)
                .permitAll()
            .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/home")
                .deleteCookies("JSEESIONID", "likelion_login_cookie")
                .invalidateHttpSession(true)
                .addLogoutHandler(ssoLogoutHandler)
                .permitAll()
            .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(this.oAuth2UserService)
                .and()
                .defaultSuccessUrl("/home")
            .and()
                .oauth2Client()
        ;
    }
}
