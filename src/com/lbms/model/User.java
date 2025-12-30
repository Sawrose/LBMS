package com.lbms.model;

public class User {

    private final int id;
    private String username;
    private String role;

    // Constructor
    public User(int id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    // Optional: Setters (only if you plan to update fields)
    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
