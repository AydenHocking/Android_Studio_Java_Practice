package edu.charlotte.assignment10.models;

import java.io.Serializable;

public class User implements Serializable {
    String name;
    String ageGroup;
    Mood mood;

    public User() {
    }

    public User(String name, String ageGroup, Mood mood) {
        this.name = name;
        this.ageGroup = ageGroup;
        this.mood = mood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }
}
