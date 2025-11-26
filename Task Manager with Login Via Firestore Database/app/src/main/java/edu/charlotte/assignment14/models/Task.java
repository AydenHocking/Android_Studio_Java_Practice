package edu.charlotte.assignment14.models;

import java.io.Serializable;

public class Task implements Serializable {
    private String name;
    private String category;
    private Priority priority;
    private String id;
    private String userID;



    public Task() {
    }

    public Task(String name, String category, Priority priority, String id, String userID) {
        this.name = name;
        this.category = category;
        this.priority = priority;
        this.id = id;
        this.userID = userID;
    }

    public Task(String name, String category, Priority priority) {
        this.name = name;
        this.category = category;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Task(String name, String category, Priority priority, String userID) {
        this.name = name;
        this.category = category;
        this.priority = priority;
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", priority=" + priority +
                ", id='" + id + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }
}
