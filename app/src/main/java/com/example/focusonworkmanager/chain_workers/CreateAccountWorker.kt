package com.example.focusonworkmanager.chain_workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class CreateAccountWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    companion object{
        private const val TAG = "CreateAccountWorker"
    }

    override suspend fun doWork(): Result {

        Log.d(TAG, "doWork: Creating account...")

        delay(3000)

        Log.d(TAG, "doWork: Account created successfully !!!")

        return Result.success()
    }

}