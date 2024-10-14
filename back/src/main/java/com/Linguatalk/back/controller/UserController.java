package com.Linguatalk.back.controller;

import com.Linguatalk.back.model.User;
import com.Linguatalk.back.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        logger.info("Received request to register user with login: {}", user.getLogin());

        // Проверка, существует ли пользователь с таким логином
        if (userService.findUserByLogin(user.getLogin()).isPresent()) {
            logger.info("User with this login is already exist: {}", user.getLogin());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        User createdUser = userService.createUser(user.getLogin(), user.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity <String> loginUser(@RequestBody User user) {
        logger.info("Received request to login user with login: {}", user.getLogin());
        boolean isAuthenticated = userService.authenticateUser(user.getLogin(), user.getPassword());

        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login or password");
        }
    }
}
