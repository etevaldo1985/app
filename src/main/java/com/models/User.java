package com.models;

public class User {
    private String username;
    private String role;
    private int userId;
    private int personId;
    private int credentialsId;

    // Constructor
    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String password) {
        this.role = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int id) {
        this.userId = id;
    }
    public int getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(int id) {
        this.credentialsId = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
