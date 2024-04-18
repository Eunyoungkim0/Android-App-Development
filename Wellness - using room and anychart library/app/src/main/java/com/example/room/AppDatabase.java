package com.example.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserLog.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserLogDao userLogDao();
}