package edu.charlotte.assignment11.models;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDAO taskDAO();
}
