package dev.kalmh.basic.controller;

import dev.kalmh.basic.auth.AuthenticationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final AuthenticationFacade authFacade;

    public HomeController(
            AuthenticationFacade authenticationFacade
    ) {
        this.authFacade = authenticationFacade;
    }

    @GetMapping
    public String root() {return "redirect:/home";}

    @GetMapping("home")
    public String home() {
        try {
            logger.info("connected user : {}",
                    authFacade.getUserName());
            logger.info(authFacade.getAuthentication().getClass().toString());
        } catch (NullPointerException e) {
            logger.info("no user logged in");
        }

        return "index";
    }
}
