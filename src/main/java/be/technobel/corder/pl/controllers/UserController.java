package be.technobel.corder.pl.controllers;

import be.technobel.corder.bl.UserService;
import be.technobel.corder.dal.models.User;
import be.technobel.corder.pl.models.forms.UserForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void createUser(@RequestBody UserForm user) {
        userService.create(user);
    }
}