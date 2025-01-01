package com.example.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User extends Muser{

    @Transient
    public static final String SEQUENCE_NAME="user_sequence";

    @Id
    private String user_id;

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                '}';
    }

}
