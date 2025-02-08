package com.example.backend.service;


import com.example.backend.model.User;
import com.example.backend.model.UserPrincipal;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            System.out.println("User not found with email: " + email);
            return new UsernameNotFoundException("User not found with email: " + email);
        });
        // Replace with your own logic to load user details from the database

        return new UserPrincipal(user);
    }
}
