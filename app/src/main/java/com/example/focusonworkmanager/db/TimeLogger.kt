package com.example.focusonworkmanager.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timeLogger")
data class TimeLogger(
    @PrimaryKey(autoGenerate = true)
    val timeId: Int = 0,
    val loggedTime: String
)
