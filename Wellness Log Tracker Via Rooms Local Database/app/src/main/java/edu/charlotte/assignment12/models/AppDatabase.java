package edu.charlotte.assignment12.models;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Wellness.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WellnessDao WellnessDao();
}
