package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document

public class Muser {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String caption;
    private String userImage;
    private String followers[];
    private String following[];
    private String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
}
