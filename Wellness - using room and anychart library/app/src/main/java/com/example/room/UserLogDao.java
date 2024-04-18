package com.example.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserLogDao {
    @Query("SELECT * FROM userlog")
    List<UserLog> getAll();

    @Query("SELECT * FROM userlog ORDER BY date DESC")
    List<UserLog> getAllOrderByDate();

    @Insert
    void insertAll(UserLog... userLogs);

    @Delete
    void delete(UserLog log);
}
