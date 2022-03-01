package com.example.focusonworkmanager.chain_workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class UpdateProfileWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    companion object{
        private const val TAG = "UpdateProfileWorker"
    }

    override suspend fun doWork(): Result {

        Log.d(TAG, "doWork: Start updating profile...")

        delay(3000)

        Log.d(TAG, "doWork: Profile updated !!!")

        return Result.success()
    }

}