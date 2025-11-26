package edu.charlotte.taskstabapp.models;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private Date date;
    private String title;
    private String priority;
    private String id;

    public Task() {
    }

    public Task(Date date, String title, String priority, String id) {
        this.date = date;
        this.title = title;
        this.priority = priority;
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "date=" + date +
                ", title='" + title + '\'' +
                ", priority='" + priority + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
