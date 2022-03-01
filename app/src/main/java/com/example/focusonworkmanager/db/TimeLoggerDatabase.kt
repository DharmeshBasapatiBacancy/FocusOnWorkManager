package com.example.focusonworkmanager.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TimeLogger::class], version = 1, exportSchema = false)
abstract class TimeLoggerDatabase : RoomDatabase() {

    abstract fun timeLoggerDao(): TimeLoggerDao

}