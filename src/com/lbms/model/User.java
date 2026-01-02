package com.lbms.model;

public class User {

    private final int id;
    private String username;
    private String role;
    private String phone;

    // Constructor
    public User(int id, String username, String role, String phone) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.phone =  phone;
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

    public String getPhone() {
        return phone;
    }

    // Optional: Setters (only if you plan to update fields)
    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
