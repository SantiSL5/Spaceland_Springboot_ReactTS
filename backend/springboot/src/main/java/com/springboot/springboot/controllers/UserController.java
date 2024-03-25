package com.springboot.springboot.controllers;


import com.springboot.springboot.model.*;
import com.springboot.springboot.requests.user.login.LoginRequest;
import com.springboot.springboot.requests.user.login.LogoutRequest;
import com.springboot.springboot.requests.user.login.RegisterRequest;
import com.springboot.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.springboot.springboot.repository.UserRepository;
import com.springboot.springboot.security.jwt.AuthTokenFilter;
import com.springboot.springboot.repository.BlacklistTokenRepository;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/profile")
    public ResponseEntity<User> profile() {
        return userService.profile();
    }

    @PostMapping("/user/register")
    public ResponseEntity registerUser(@RequestBody RegisterRequest registerUser) {
        return userService.register(registerUser);
    }

    @PostMapping("/user/login")
    public ResponseEntity loginUser(@RequestBody LoginRequest loginUser) {
        return userService.login(loginUser);
    }

    @PostMapping("/user/logout")
    public ResponseEntity logoutUser(@RequestBody LogoutRequest logoutRequest) {
        return userService.logoutUser(logoutRequest);
    }

}
