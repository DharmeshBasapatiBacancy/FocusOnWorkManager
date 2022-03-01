package com.example.focusonworkmanager.chain_workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class ExploreProductsWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    companion object {
        private const val TAG = "ExploreProductsWorker"
    }

    override suspend fun doWork(): Result {

        Log.d(TAG, "doWork: Exploring products from list...")

        delay(5000)

        Log.d(TAG, "doWork: Product added into cart.")

        return Result.success()
    }

}