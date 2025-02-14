package com.example.backend.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Muser {

    private String firstName;
    private String lastName;
    private String name;
    private String email;
    private String password;
    private String caption;
    private String userImage;
    private String provider;
    private String followers[];
    private String following[];
    
    private List<String> likedPosts = new ArrayList<>();
    private List<String> savedPosts = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", caption='" + caption + '\'' +
                ", provider='" + provider + '\'' +
                ", userImage='" + userImage + '\'' +
                ", followers='" + followers + '\'' +
                ", following='" + following + '\'' +
                '}';
    }
}


