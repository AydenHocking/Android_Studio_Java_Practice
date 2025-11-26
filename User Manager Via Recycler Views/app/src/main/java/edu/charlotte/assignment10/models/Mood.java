package edu.charlotte.assignment10.models;

import java.io.Serializable;

public class Mood implements Serializable {
    String name;
    int imageResourceId;

    public Mood() {
    }

    public Mood(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
