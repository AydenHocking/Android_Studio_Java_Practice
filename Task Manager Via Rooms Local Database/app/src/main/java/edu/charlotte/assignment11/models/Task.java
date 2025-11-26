package edu.charlotte.assignment11.models;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo
    public String name;
    @ColumnInfo
    public String category;
    @ColumnInfo
    public String priorityName;
    @ColumnInfo
    public int priorityLevel;

    public Task(long id, String name, String category, String priorityName, int priorityLevel) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.priorityName = priorityName;
        this.priorityLevel = priorityLevel;
    }

    public Task() {}

    public Task(String name, String selectedCategory, Priority selectedPriority) {
        this.name = name;
        this.category = selectedCategory;
        this.priorityName = selectedPriority.getName();
        this.priorityLevel = selectedPriority.getLevel();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPriorityName() { return priorityName; }
    public void setPriorityName(String priorityName) { this.priorityName = priorityName; }

    public int getPriorityLevel() { return priorityLevel; }
    public void setPriorityLevel(int priorityLevel) { this.priorityLevel = priorityLevel; }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", priorityName='" + priorityName + '\'' +
                ", priorityLevel=" + priorityLevel +
                '}';
    }
}
