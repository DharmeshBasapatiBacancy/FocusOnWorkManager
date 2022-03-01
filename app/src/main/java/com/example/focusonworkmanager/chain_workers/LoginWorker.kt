package com.example.focusonworkmanager.chain_workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class LoginWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    companion object{
        private const val TAG = "LoginWorker"
    }

    override suspend fun doWork(): Result {

        Log.d(TAG, "doWork: Logging into account...")

        delay(3000)

        Log.d(TAG, "doWork: Logged in successfully !!!")

        return Result.success()
    }

}