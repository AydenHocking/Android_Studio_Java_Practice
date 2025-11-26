package edu.charlotte.taskstabapp.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class DataStore {
    public static ArrayList<Task> getTasks(){
        ArrayList<Task> tasks = new ArrayList<>();
        String[] priorities = {"HIGH", "MEDIUM", "LOW"};
        Random rand = new Random();
        for(int i = 1; i <= 100; i++){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, rand.nextInt(90));
            String priority = priorities[rand.nextInt(3)];
            tasks.add(new Task(cal.getTime(), "Task " + i, priority, UUID.randomUUID().toString()));
        }
        return tasks;
    }
}
