package edu.charlotte.assignment12.models;

import java.io.Serializable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wellness")
public class Wellness implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "date_time")
    public String dateTime;

    @ColumnInfo(name = "sleep_hours")
    public double sleepHours;

    @ColumnInfo(name = "sleep_quality")
    public int sleepQuality;

    @ColumnInfo(name = "exercise_hours")
    public double exerciseHours;

    @ColumnInfo(name = "weight")
    public double weight;

    public Wellness() {}

    public Wellness(String dateTime, double sleepHours, int sleepQuality, double exerciseHours, double weight) {
        this.dateTime = dateTime;
        this.sleepHours = sleepHours;
        this.sleepQuality = sleepQuality;
        this.exerciseHours = exerciseHours;
        this.weight = weight;
    }

    public Wellness(long id, String dateTime, double sleepHours, int sleepQuality, double exerciseHours, double weight) {
        this.id = id;
        this.dateTime = dateTime;
        this.sleepHours = sleepHours;
        this.sleepQuality = sleepQuality;
        this.exerciseHours = exerciseHours;
        this.weight = weight;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }

    public double getSleepHours() { return sleepHours; }
    public void setSleepHours(double sleepHours) { this.sleepHours = sleepHours; }

    public int getSleepQuality() { return sleepQuality; }
    public void setSleepQuality(int sleepQuality) { this.sleepQuality = sleepQuality; }

    public double getExerciseHours() { return exerciseHours; }
    public void setExerciseHours(double exerciseHours) { this.exerciseHours = exerciseHours; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    @Override
    public String toString() {
        return "Wellness{" +
                "id=" + id +
                ", dateTime='" + dateTime + '\'' +
                ", sleepHours=" + sleepHours +
                ", sleepQuality=" + sleepQuality +
                ", exerciseHours=" + exerciseHours +
                ", weight=" + weight +
                '}';
    }
}
