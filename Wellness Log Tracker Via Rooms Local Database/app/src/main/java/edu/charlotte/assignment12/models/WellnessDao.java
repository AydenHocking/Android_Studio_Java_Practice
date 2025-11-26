package edu.charlotte.assignment12.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WellnessDao {

    @Insert
    long insert(Wellness wellness);

    @Update
    void update(Wellness wellness);

    @Delete
    void delete(Wellness wellness);

    @Query("DELETE FROM wellness")
    void deleteAll();

    @Query("SELECT * FROM wellness ORDER BY id ASC")
    List<Wellness> getAll();

    @Query("SELECT * FROM wellness ORDER BY sleep_hours DESC")
    List<Wellness> getAllSortedBySleepDesc();

    @Query("SELECT * FROM wellness ORDER BY exercise_hours DESC")
    List<Wellness> getAllSortedByExerciseDesc();

    @Query("SELECT * FROM wellness ORDER BY date_time DESC")
    List<Wellness> getAllSortedByDateDesc();
    @Query("SELECT * FROM wellness ORDER BY date_time ASC")
    List<Wellness> getAllSortedByDateAsc();

    @Query("SELECT * FROM wellness WHERE id = :id LIMIT 1")
    Wellness findById(long id);
}
