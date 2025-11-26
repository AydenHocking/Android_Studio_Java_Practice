package edu.uncc.assignment06;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    String name;
    String date;
    String priority;
    public Task(String name, String date, String priority) {
        this.name = name;
        this.date = date;
        this.priority = priority;
    }

    public Task() {
    }

    public String getName(){
        return name;
    }
    public String getDate(){
        return date;
    }
    public String getPriority(){
        return priority;
    }


}

