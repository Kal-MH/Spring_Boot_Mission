package dev.kalmh.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SsoLoginController {
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
}
