package dev.kalmh.community.controller;

import dev.kalmh.community.controller.dto.UserDto;
import dev.kalmh.community.infra.AuthenticationFacade;
import dev.kalmh.community.service.UserService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@Controller
@RequestMapping("user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authFacade;

    public UserController(
            @Autowired UserService userService,
            @Autowired PasswordEncoder passwordEncoder,
            @Autowired AuthenticationFacade authFacade
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authFacade = authFacade;
    }

    @GetMapping("/login")
    public String viewLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value="exception", required = false) String exception,
            Model model
    ) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login-form";
    }
    @GetMapping("/signup")
    public String viewSignup() {
        return "signup-form";
    }

    @PostMapping("/signup")
    public String signupUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("password_check") String passwordCheck,
            @RequestParam(value = "is_shop_owner", required = false) boolean isShopOwner,
            Model model
    ) {
        if (!password.equals(passwordCheck) || username.equals("")
                || password.equals("") || passwordCheck.equals("")) {
            logger.info("User Signup Error : Not Valid input value");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (this.userService.checkUsernameDuplicate(username)) {
            model.addAttribute("username", username);
            model.addAttribute("error", "true");
            model.addAttribute("exception", "Duplicate username");
            return "signup-form";
        }

        UserDto newUser = new UserDto();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setIsShopOwner(isShopOwner);
        this.userService.createUser(newUser);

        return "redirect:/home";
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(this.userService.createUser(userDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> readUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.userService.readUser(id));
    }

    @GetMapping
    public ResponseEntity<Collection<UserDto>> readUserAll(){
        return ResponseEntity.ok(this.userService.readUserAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto){
        this.userService.updateUser(id, userDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> DeleteUser(@PathVariable("id") Long id){
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
