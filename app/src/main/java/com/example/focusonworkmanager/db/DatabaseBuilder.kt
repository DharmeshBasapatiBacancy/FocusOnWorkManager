package com.example.focusonworkmanager.db

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private var DB_INSTANCE: TimeLoggerDatabase? = null

    fun getInstance(context: Context): TimeLoggerDatabase? {
        if (DB_INSTANCE == null) {
            synchronized(TimeLoggerDatabase::class) {
                DB_INSTANCE = buildDbInstance(context)
            }
        }
        return DB_INSTANCE!!
    }

    private fun buildDbInstance(context: Context): TimeLoggerDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TimeLoggerDatabase::class.java,
            "timeloggerdb"
        ).build()
    }
}

