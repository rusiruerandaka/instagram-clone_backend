package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.util.JwtTokenUtil;
import com.example.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend.service.UserService;
import com.example.backend.service.FollowServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final FollowServiceInterface followService;

    @Autowired
    private UserRepository userRepository;

    public UserController(JwtTokenUtil jwtTokenUtil, UserService userService, FollowServiceInterface followService) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.followService = followService;
    }

    @PostMapping("/signup")
    public User signUp(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.verify(user);
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody User user){
        try {
            User createdUser = userService.addUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        User user = userService.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            String token = jwtTokenUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PostMapping("/{userId}/follow/{followUserId}")
    public User followUser(@PathVariable String userId, @PathVariable String followUserId) {
        return followService.followUser(userId, followUserId);
    }

    @PostMapping("/{userId}/unfollow/{unfollowUserId}")
    public User unfollowUser(@PathVariable String userId, @PathVariable String unfollowUserId) {
        return followService.unfollowUser(userId, unfollowUserId);
    }

    @GetMapping("/getAllUsers")
    public List<User> getUser(){
        return (List<User>) userService.getUser();
    }

    @GetMapping("/getUserById/{id}")
    public User findById(@PathVariable String id){
        return userService.getUserById(id);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User user){
        User updateUser = userService.updateUser(id,user);
        return ResponseEntity.ok(updateUser);
    }

    @GetMapping("/getUserByEmail/{email}")
    public User findByEmail(@PathVariable String email){
        return userRepository.findByEmail(email);
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable String id){
        return userService.deleteUser(id);
    }

    @GetMapping("/searchUser/{name}")
    public List<User> searchUser(@RequestParam("name") String name){
        return userRepository.findByFirstNameContainingIgnoreCase(name);
    }
}