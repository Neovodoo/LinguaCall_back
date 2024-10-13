package com.Linguatalk.back.controller;

import com.Linguatalk.back.model.User;
import com.Linguatalk.back.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        logger.info("Received request to register user with login: {}", user.getLogin());
        return userService.createUser(user.getLogin(), user.getPassword());
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        logger.info("Received request to login user with login: {}", user.getLogin());
        boolean isAuthenticated = userService.authenticateUser(user.getLogin(), user.getPassword());
        return isAuthenticated ? "Login successful" : "Invalid login or password";
    }
}
