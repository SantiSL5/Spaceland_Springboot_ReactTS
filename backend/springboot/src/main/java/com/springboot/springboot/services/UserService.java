package com.springboot.springboot.services;

import com.google.gson.Gson;
import com.springboot.springboot.model.BlacklistToken;
import com.springboot.springboot.model.User;
import com.springboot.springboot.model.UserAndToken;
import com.springboot.springboot.repository.BlacklistTokenRepository;
import com.springboot.springboot.repository.UserRepository;
import com.springboot.springboot.requests.user.login.LoginRequest;
import com.springboot.springboot.requests.user.login.LogoutRequest;
import com.springboot.springboot.requests.user.login.RegisterRequest;
import com.springboot.springboot.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private BlacklistTokenRepository BlacklistTokenRepository;

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final Gson gson = new Gson();

    public User token_user() throws Exception{
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            Optional<User> optional = userRepository.findByUsername(userDetails.getUsername());
            return optional.isPresent() ? optional.get() : null;
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public ResponseEntity profile() {
        try {
            User user = token_user();
            if (user == null) return ResponseEntity.notFound().build();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity register(RegisterRequest registerUser) {
        try {
            if (userRepository.existsByUsername(registerUser.getUsername()) > 0 && userRepository.existsByEmail(registerUser.getEmail()) > 0 ) {
                return new ResponseEntity<>("Email and username already taked", HttpStatus.CONFLICT);
            }
            if (userRepository.existsByUsername(registerUser.getUsername()) > 0) {
                return new ResponseEntity<>("Username already taked", HttpStatus.CONFLICT);
            }
            if (userRepository.existsByEmail(registerUser.getEmail()) > 0) {
                return new ResponseEntity<>("Email already taked", HttpStatus.CONFLICT);
            }

            User user = new User();
            user.setEmail(registerUser.getEmail());
            user.setUsername(registerUser.getUsername());
            user.setRole("CLIENT");
            user.setEnabled(true);
            user.setPhoto("https://static.productionready.io/images/smiley-cyrus.jpg");
            user.setPassword(encoder.encode(registerUser.getPassword()));
            user.setCreated_at(new Timestamp(System.currentTimeMillis()));
            user.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity login(LoginRequest loginUser) {
        try {
            if (userRepository.existsByEmail(loginUser.getEmail()) == 0) {
                return new ResponseEntity<>("Email not found", HttpStatus.NOT_FOUND);
            }
            User user = userRepository.findByEmail(loginUser.getEmail()).get();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), loginUser.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            return new ResponseEntity<>(new UserAndToken(user, jwt), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Wrong password", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity logoutUser(LogoutRequest logoutRequest) {
        try {
            String token = logoutRequest.getToken();

            if (BlacklistTokenRepository.TokenExist(token) == 0) {
                BlacklistToken blacklistToken = new BlacklistToken();
                blacklistToken.setToken(token);
                BlacklistTokenRepository.save(blacklistToken);
            }
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
