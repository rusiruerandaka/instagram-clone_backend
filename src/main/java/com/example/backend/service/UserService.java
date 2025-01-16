package com.example.backend.service;

import com.example.backend.model.DatabaseSequence;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.core.user.OAuth2User;
import com.example.backend.model.RegistrationMail;
import com.example.backend.model.User;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;



@Service
public class UserService {
    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authmanager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private BCryptPasswordEncoder encoder;


    public String verify(User user) {

            Authentication authentication = authmanager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getEmail());
            } else {
                return "Authentication failed";
            }


    }
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        user.setUser_id(generateSequence(User.SEQUENCE_NAME));
        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        RegistrationMail registrationMail = new RegistrationMail();
        registrationMail.setSubject("Registration Confirmation");
        registrationMail.setName(savedUser.getFirstName() + " " + savedUser.getLastName());

        return user;
    }

    public User registerOAuth2User(OAuth2User oauthUser) {
        String userId = oauthUser.getAttribute("id");
        String userName = oauthUser.getAttribute("name");


        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
          return existingUser.get();
        }

        // Register a new user
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        userRepository.save(user);

        
        return user;
    }


    public User logout() {
        return null;
    }


    public Optional<User> getUserById(String id){

        return userRepository.findById(id);
    }

    public List<User> getUser() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User updateUser(String id, User user) {
        User existingUser = userRepository.findById(id).orElseThrow(
            () -> new RuntimeException()
        );
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(encoder.encode(user.getPassword()));
        existingUser.setCaption(user.getCaption());
        existingUser.setUserImage(user.getUserImage());
        
        return userRepository.save(existingUser);
    }

    public String deleteUser(String id){
        userRepository.deleteById(id);
        return "User deleted with id: "+id;
    }


    public String generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(
                query(where("_id").is(seqName)),
                new Update().inc("seq", 1),
                options().returnNew(true).upsert(true),
                DatabaseSequence.class
        );
        return "U" + (!Objects.isNull(counter) ? counter.getSeq() : 1);
    }


    public List<User> searchByName(String name){
        return userRepository.findByFirstNameContainingIgnoreCase(name);
    }

    public User addLikes(String userId, String postId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new RuntimeException()
        );
        user.getLikedPosts().add(postId);
        return userRepository.save(user);
    }

    public User removeLikes(String userId, String postId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new RuntimeException()
        );
        user.getLikedPosts().remove(postId);
        return userRepository.save(user);
    }

}