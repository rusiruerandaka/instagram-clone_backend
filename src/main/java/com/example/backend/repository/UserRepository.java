package com.instagram.Instagram.clone.repository;

import com.instagram.Instagram.clone.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
    List<User> findByNameContainingIgnoreCase(String firstname);

}
