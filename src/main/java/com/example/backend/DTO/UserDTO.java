package com.example.backend.DTO;

public class UserDTO {
    private String id;
    private String name;

    // Default constructor (required for frameworks like Jackson)
    public UserDTO() {}

    // Parameterized constructor for easy initialization
    public UserDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter for ID
    public String getId() {
        return id;
    }

    // Setter for ID
    public void setId(String id) {
        this.id = id;
    }

    // Getter for Name
    public String getName() {
        return name;
    }

    // Setter for Name
    public void setName(String name) {
        this.name = name;
    }

    // Optional: toString method for logging/debugging
    @Override
    public String toString() {
        return "UserDTO{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}


