package com.example.backend.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document

public class Muser {

    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String caption;
    @Getter
    @Setter
    private String userImage;
    @Getter
    @Setter
    private String folloers[];
    @Getter
    @Setter
    private String following[];

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", caption='" + caption + '\'' +
                ", userImage='" + userImage + '\'' +
                ", folloers='" + folloers + '\'' +
                ", following='" + following + '\'' +
                '}';
    }


}


