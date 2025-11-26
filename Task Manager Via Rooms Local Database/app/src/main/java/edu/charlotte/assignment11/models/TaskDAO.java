package edu.charlotte.assignment11.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDAO {

    @Insert
    long insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("DELETE FROM tasks")
    void deleteAll();

    @Query("SELECT * FROM tasks")
    List<Task> getAll();

    @Query("SELECT * FROM tasks ORDER BY priorityLevel DESC")
    List<Task> getAllSortedDesc();

    @Query("SELECT * FROM tasks ORDER BY priorityLevel ASC")
    List<Task> getAllSortedAsc();

    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    Task findById(long id);
}
