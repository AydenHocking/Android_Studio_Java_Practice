package com.example.usercreationmanagingactivities;

import java.io.Serializable;
/*
Assignment 3
Ayden Hocking
UserActivity.java
 */
public class User implements Serializable {
    String name;
    String email;
    String role;
    public User(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public User() {
    }

    public String getRole(){
        return role;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }


}
