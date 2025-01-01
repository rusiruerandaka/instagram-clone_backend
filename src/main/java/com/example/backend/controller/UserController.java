package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/users")

public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        User createduser = userService.register(user);
        return new ResponseEntity<>(createduser, HttpStatus.CREATED);
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
        }}



    @GetMapping("/getAllUsers")
    public List<User> getUser(){
        return (List<User>) userService.getUser();
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
