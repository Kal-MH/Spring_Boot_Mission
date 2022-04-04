package dev.kalmh.basic.controller;

import dev.kalmh.basic.controller.dto.UserDto;
import dev.kalmh.basic.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService;}

    //read
    @GetMapping("{id}")
    public @ResponseBody ResponseEntity<UserDto> readUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.userService.readUser(id));
    }
    //read all
    @GetMapping
    public @ResponseBody ResponseEntity<Collection<UserDto>> readUserAll() {
        return ResponseEntity.ok(this.userService.readUserAll());
    }
    //update
    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable("id") Long id,
            @RequestBody UserDto dto
    ) {
        this.userService.updateUser(id, dto);
        return ResponseEntity.noContent().build();
    }
    //delete
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    //new
}
