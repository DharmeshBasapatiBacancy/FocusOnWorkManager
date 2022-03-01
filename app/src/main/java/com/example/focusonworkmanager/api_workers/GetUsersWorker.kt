package com.example.focusonworkmanager.api_workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import network.RetrofitBuilder

class GetUsersWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    companion object {
        private const val TAG = "GetUsersWorker"
    }

    override suspend fun doWork(): Result {

        Log.d(TAG, "doWork: Fetching users from API...")

        val apiResponse = RetrofitBuilder.apiService.getUsers()

        return if (apiResponse.isSuccessful) {
            Log.d(TAG, "doWork: API Success - ${apiResponse.body().toString()}")
            Result.success()
        } else {
            Log.d(TAG, "doWork: API Failure - ${apiResponse.errorBody().toString()}")
            Result.failure()
        }

    }
}