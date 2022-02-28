package com.example.focusonworkmanager.db

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface TimeLoggerDao {

    @Insert
    suspend fun insertTime(timeLogger: TimeLogger)

}