package com.baimamboukar.java.app.models;

public class User {
    private  String id;
    private  String name;
    private  String major;

    // Constructor
    public User(String id, String name, String major) {
        this.id = id;
        this.name = name;
        this.major = major;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    // Setters (not typically used for final fields, but included for completeness)
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
