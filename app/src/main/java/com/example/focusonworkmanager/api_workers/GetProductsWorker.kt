package com.example.focusonworkmanager.api_workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import network.RetrofitBuilder

class GetProductsWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    companion object {
        private const val TAG = "GetProductsWorker"
    }

    override suspend fun doWork(): Result {

        Log.d(TAG, "doWork: Fetching products from API...")

        val apiResponse = RetrofitBuilder.apiService.getProducts()

        return if (apiResponse.isSuccessful) {
            Log.d(TAG, "doWork: API Success - ${apiResponse.body().toString()}")
            Result.success()
        } else {
            Log.d(TAG, "doWork: API Failure - ${apiResponse.errorBody().toString()}")
            Result.failure()
        }

    }
}