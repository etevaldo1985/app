package com.models;

public class Credentials {
    private String username;
    private String password;
    private int credentialsId;
    private int personId;

    // Constructor
    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
