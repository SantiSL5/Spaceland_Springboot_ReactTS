package com.springboot.springboot.controllers;


import com.springboot.springboot.model.*;
import com.springboot.springboot.requests.general.DeleteManyRequest;
import com.springboot.springboot.requests.user.create.NewUserRequest;
import com.springboot.springboot.requests.user.update.UpdateUserRequest;
import com.springboot.springboot.requests.user.login.LoginRequest;
import com.springboot.springboot.requests.user.login.LogoutRequest;
import com.springboot.springboot.requests.user.login.RegisterRequest;
import com.springboot.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/admin/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable(required = true) String id) {
        try {
            User user = userService.getUser(id);
            if (user == null) return ResponseEntity.badRequest().body("User not found");
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @GetMapping("/admin/user")
    public ResponseEntity getAllUsers(@RequestParam(value = "limit", required = false, defaultValue = "0") Integer limit,
                                      @RequestParam(value = "offset", required = false) Integer offset) {
        return userService.getUsers(limit,offset);
    }

    @PostMapping("/admin/user")
    public ResponseEntity createUser(@RequestBody NewUserRequest newUserRequest) {
            return userService.createUser(newUserRequest);
    }
    @PutMapping("/admin/user/{id}")
    public ResponseEntity updateUser(@PathVariable String id, @RequestBody UpdateUserRequest updatedUser) {
            return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/admin/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            User user = userService.getUser(id);
            if (user == null) return ResponseEntity.badRequest().body("User not found");
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("User deletion failed");
        }
    }

    @DeleteMapping("/admin/user/deleteMany")
    public ResponseEntity<?> deleteManyUser(@RequestBody DeleteManyRequest manyUsers) {
            return userService.deleteManyUsers(manyUsers.getIds());
    }
}
