package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import com.example.backend.service.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@CrossOrigin
@RequestMapping("/users")

public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        User createduser = userService.register(user);
        return new ResponseEntity<>(createduser, HttpStatus.CREATED);
    }

    @GetMapping("/oauth2/login/fb")
    public ResponseEntity<?> handleOAuth2Login(@AuthenticationPrincipal OAuth2User oauthUser, HttpServletResponse response) {
    if (oauthUser == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OAuth2User not found");
    }

    Map<String, Object> attributes = oauthUser.getAttributes();
    System.out.println("OAuth2User Attributes: " + attributes);

    String name = (String) attributes.get("name");
    String email = (String) attributes.get("email");

    if (name == null || email == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name or email not found in OAuth2User attributes");
    }

    User user = userService.registerOAuth2User(oauthUser);

    String jwtToken = jwtService.generateToken(user.getEmail());
    response.addHeader("Authorization", "Bearer " + jwtToken);

    return new ResponseEntity<>(user, HttpStatus.CREATED);
}



    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.verify(user);
    }

    @GetMapping("/logout")
    public String logout() {
        return "Logged out";
    }

    @GetMapping("/getAllUsers")
    public List<User> getUser(){
        return (List<User>) userService.getUser();
    }

    @GetMapping("/generateToken")
    public String generateJwtToken(OAuth2User oauthUser) {
    String email = oauthUser.getAttribute("email");
    String jwtToken = jwtService.generateToken(email);
    return jwtToken;
    }

    @GetMapping("/getUserById/{id}")
    public Optional<User> findById(@PathVariable String id){
        return userService.getUserById(id);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User user){
        User updateUser = userService.updateUser(id,user);
        return ResponseEntity.ok(updateUser);
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email){
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable String id){
        return userService.deleteUser(id);
    }

    @GetMapping("/searchUser/{name}")
    public List<User> searchUser(@PathVariable("name") String name){
        return userRepository.findByFirstNameContainingIgnoreCase(name);
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<String[]> getFollowers(@PathVariable String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user.getFollowers());
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<String[]> getFollowing(@PathVariable String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user.getFollowing());
    }

    @PostMapping("/addLikes/{userId}/{postId}")
    public ResponseEntity<?> addLikes(@PathVariable String userId, @PathVariable String postId){
        try {
            User user = userService.addLikes(userId, postId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add likes: " + e.getMessage());
        }
    }

    @DeleteMapping("/removeLikes/{userId}/{postId}")
    public ResponseEntity<?> removeLikes(@PathVariable String userId, @PathVariable String postId){
        try {
            User user = userService.removeLikes(userId, postId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove likes: " + e.getMessage());
        }
    }

    @GetMapping("/getSavedPosts/{userId}")
    public ResponseEntity<?> getSavedPosts(@PathVariable String userId){
        try {
            List<String> savedPosts = userService.getSavedPosts(userId);
            return ResponseEntity.ok(savedPosts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get saved posts: " + e.getMessage());
        }
    }
    
}
