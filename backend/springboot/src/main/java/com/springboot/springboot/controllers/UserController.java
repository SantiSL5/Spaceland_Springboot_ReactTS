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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlacklistTokenRepository BlacklistTokenRepository;

    @Autowired
    private AuthTokenFilter authTokenFilter;

    @GetMapping("/user/profile")
    public ResponseEntity<User> profile() {
        try {
            User user = userService.token_user();
            if (user == null) return ResponseEntity.notFound().build();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/user/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterRequest registerUser) {
        try {
            if (userRepository.existsByUsername(registerUser.getUsername()) > 0 || userRepository.existsByEmail(registerUser.getEmail()) > 0 ) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                userService.register(registerUser);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        } catch (Exception e) {
            System.err.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<UserAndToken> loginUser(@RequestBody LoginRequest loginUser) {
        try {
            if (userRepository.existsByEmail(loginUser.getEmail()) < 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            User user = userRepository.findByEmail(loginUser.getEmail()).get();
            UserAndToken userToken = userService.login(user, loginUser);
            return new ResponseEntity<>(userToken, HttpStatus.OK);

        } catch (Exception e) {
            System.err.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/logout")
    public ResponseEntity<?> logoutUser(@RequestBody LogoutRequest logoutRequest) {
        try {
            String token = logoutRequest.getToken();

            if (BlacklistTokenRepository.TokenExist(token) == 0) {
                BlacklistToken blacklistToken = new BlacklistToken();
                blacklistToken.setToken(token);
                BlacklistTokenRepository.save(blacklistToken);
            }
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            System.err.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
