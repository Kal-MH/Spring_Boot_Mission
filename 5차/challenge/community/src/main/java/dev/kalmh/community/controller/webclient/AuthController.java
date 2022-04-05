package dev.kalmh.community.controller.webclient;

import dev.kalmh.community.service.webclient.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {this.authService = authService;}

//    @GetMapping("request-userinfo")
//    public UserInfoDto getUserInfo() {
//        return this.authService.getUserInfo();
//    }

}
