package dev.kalmh.challenge;

import dev.kalmh.challenge.dto.UserDto;
import dev.kalmh.challenge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(
            @Autowired UserService userService
    ) {
        this.userService = userService;
    }

    //POST
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserDto dto) {this.userService.createUser(dto);}

    //GET ALL
    @GetMapping("")
    public List<UserDto> readUserAll() {return this.userService.readUserAll();}

    //GET
    @GetMapping("{id}")
    public UserDto readUser(@PathVariable("id") int id) {
        return this.userService.readUser(id);
    }

    //UPDATE
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUser(
            @PathVariable("id") int id,
            @RequestBody UserDto postDto
    ) {
        this.userService.updateUser(id, postDto);
    }
    //DELETE
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUser(
            @PathVariable("id") int id
    ) {
        this.userService.deleteUser(id);
    }
}
