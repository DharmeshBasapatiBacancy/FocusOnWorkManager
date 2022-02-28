package com.example.focusonworkmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.focusonworkmanager.db.DatabaseBuilder
import com.example.focusonworkmanager.db.TimeLogger
import java.util.*

class TimeLoggerWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val date = Calendar.getInstance()

        Log.d(TAG, "doWork: Current Time and Date - ${date.time}")

        DatabaseBuilder.getInstance(applicationContext)?.timeLoggerDao()
            ?.insertTime(TimeLogger(loggedTime = date.time.toString()))

        return Result.success()
    }

    companion object {
        private const val TAG = "TimeLoggerWorker"
    }
}