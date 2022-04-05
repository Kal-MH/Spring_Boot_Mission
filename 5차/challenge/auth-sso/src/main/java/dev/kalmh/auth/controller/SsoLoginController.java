package dev.kalmh.auth.controller;

import dev.kalmh.auth.entity.model.RedisService;
import dev.kalmh.auth.entity.model.UserInfo;
import dev.kalmh.auth.service.PublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class SsoLoginController {
    private static final Logger logger = LoggerFactory.getLogger(SsoLoginController.class);
    private final RedisService redisService;
    private final PublisherService publisherService;

    public SsoLoginController(
            RedisService redisService,
            PublisherService publisherService
    ) {
        this.redisService = redisService;
        this.publisherService = publisherService;
    }
    //SsoCookieHandler까지 거치고 난 후에 request-login경로로 들어오게 된다.
    @GetMapping("request-login")
    public String requestLogin(
            HttpServletRequest request,
            @RequestParam("request_from") String requestFrom
    ){

        String cookieValue = "";
        for (Cookie cookie: request.getCookies()) {

            if (cookie.getName().equals("likelion_login_cookie")){
                cookieValue = cookie.getValue();
                break;
            }
        }

        //쿠키는 브라우저끼리 공유가 안되기 대문에 쿼리로 보내주고 있다.
        return String.format(
                "redirect:%s?likelion_login_cookie=%s", requestFrom, cookieValue);
    }

    @GetMapping("request-userinfo")
    public @ResponseBody UserInfo requestUserInfo (
            HttpServletRequest request,
            @RequestParam("token_value") String token_value
    ) {
        logger.info("SsoController request user info : {}", token_value);
        return this.redisService.retrieveUserInfo(token_value);
    }

    @GetMapping("/")
    public void sendMessage() {
        this.publisherService.publishMessage("hello");
    }
}
