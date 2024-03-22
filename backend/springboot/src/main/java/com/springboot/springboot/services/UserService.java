package com.springboot.springboot.services;

import com.google.gson.Gson;
import com.springboot.springboot.model.User;
import com.springboot.springboot.model.UserAndToken;
import com.springboot.springboot.repository.UserRepository;
import com.springboot.springboot.requests.user.login.LoginRequest;
import com.springboot.springboot.requests.user.login.RegisterRequest;
import com.springboot.springboot.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void register(RegisterRequest registerUser) throws Exception {
        try {
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
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public UserAndToken login(User user, LoginRequest loginUser) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), loginUser.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            return new UserAndToken(jwt, user);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public User getUser(String id) throws Exception {
        try {
            return getUser(Long.parseLong(id));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public User getUser(Long id) {
        Optional<User> optional = userRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

}
