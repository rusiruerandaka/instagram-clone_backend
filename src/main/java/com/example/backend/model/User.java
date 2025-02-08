package com.example.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User extends Muser{

    @Transient
    public static final String SEQUENCE_NAME="user_sequence";

    @Id
    private String user_id;

    


    @Override
public String toString() {
    return super.toString() + ", user_id='" + user_id + '\'' + '}';
}


}
