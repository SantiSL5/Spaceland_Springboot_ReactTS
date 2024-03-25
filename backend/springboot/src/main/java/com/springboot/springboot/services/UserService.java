package com.springboot.springboot.services;

import com.google.gson.Gson;
import com.springboot.springboot.model.BlacklistToken;
import com.springboot.springboot.model.User;
import com.springboot.springboot.model.UserAndToken;
import com.springboot.springboot.repository.BlacklistTokenRepository;
import com.springboot.springboot.repository.UserRepository;
import com.springboot.springboot.requests.user.create.NewUserRequest;
import com.springboot.springboot.requests.user.login.LoginRequest;
import com.springboot.springboot.requests.user.login.LogoutRequest;
import com.springboot.springboot.requests.user.login.RegisterRequest;
import com.springboot.springboot.requests.user.update.UpdateUserRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public ResponseEntity getUsers(Integer limit, Integer offset) {
        try {
            List<User> users = new ArrayList<User>();
            if ( offset != null  && offset > 0 ) {
                getAllUsers(limit,offset).forEach(users::add);
            }else {
                getAllUsers().forEach(users::add);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> getAllUsers(Integer limit, Integer offset) {
        Pageable pageable = PageRequest.of(limit,offset);
        return userRepository.findAll(pageable);
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

    public ResponseEntity createUser(NewUserRequest newUser) {
        try {
            if (userRepository.existsByUsername(newUser.getUsername()) > 0 && userRepository.existsByEmail(newUser.getEmail()) > 0 ) {
                return new ResponseEntity<>("Email and username already taked", HttpStatus.CONFLICT);
            }
            if (userRepository.existsByUsername(newUser.getUsername()) > 0) {
                return new ResponseEntity<>("Username already taked", HttpStatus.CONFLICT);
            }
            if (userRepository.existsByEmail(newUser.getEmail()) > 0) {
                return new ResponseEntity<>("Email already taked", HttpStatus.CONFLICT);
            }
            User user = new User();
            user.setEmail(newUser.getEmail());
            user.setUsername(newUser.getUsername());
            user.setRole(newUser.getRole());
            user.setEnabled(newUser.getEnabled());
            user.setPassword(encoder.encode(newUser.getPassword()));
            user.setPhoto("https://static.productionready.io/images/smiley-cyrus.jpg");
            user.setCreated_at(new Timestamp(System.currentTimeMillis()));
            user.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity updateUser(String id, UpdateUserRequest updatedUser) {
        try {
            User user = getUser(id);
            if (user == null) return ResponseEntity.badRequest().body("User not found");;
            if (user != null) {
                if (updatedUser.getUsername() != null ) {
                    if (userRepository.existsByUsername(updatedUser.getUsername()) > 0 && !Objects.equals(user.getUsername(), updatedUser.getUsername())) {
                        return new ResponseEntity<>("Username already taked", HttpStatus.CONFLICT);
                    }
                    user.setUsername(updatedUser.getUsername());
                }
                if (updatedUser.getEmail() != null) {
                    if (userRepository.existsByEmail(updatedUser.getEmail()) > 0 && !Objects.equals(user.getEmail(), updatedUser.getEmail())) {
                        return new ResponseEntity<>("Email already taked", HttpStatus.CONFLICT);
                    }
                    user.setEmail(updatedUser.getEmail());
                }
                if (updatedUser.getPassword() != null) {
                    user.setPassword(encoder.encode(updatedUser.getPassword()));
                }
                if (updatedUser.getRole() != null) {
                    user.setRole(updatedUser.getRole());
                }
                if (updatedUser.getEnabled() != null) {
                    user.setEnabled(updatedUser.getEnabled());
                }
                user.setUpdated_at(new Timestamp(System.currentTimeMillis()));
                userRepository.save(user);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error updating user: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting user: {}", e.getMessage());
        }
    }
    
    public void deleteUser(String id) {
        try {
            Long userId = Long.parseLong(id);
            deleteUser(userId);
        } catch (Exception e) {
            logger.error("Error deleting user: {}", e.getMessage());
        }
    }

    public ResponseEntity deleteManyUsers(ArrayList<Long> ids) {
        try {
            for (Long id : ids) {
                deleteUser(id);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting user: {}", e.getMessage());
            return ResponseEntity.badRequest().body("User deletion failed");
        }
    }
}
