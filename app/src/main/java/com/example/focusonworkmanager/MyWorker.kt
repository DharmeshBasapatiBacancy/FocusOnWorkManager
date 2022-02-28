package com.example.focusonworkmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay

class MyWorker(
    context: Context,
    workerParameters: WorkerParameters
) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        Log.d(TAG, "doWork: Started")

        val num1 = inputData.getInt("KEY_ONE", 0)
        val num2 = inputData.getInt("KEY_TWO", 0)
        val addResult = num1 + num2

        Log.d(TAG, "doWork: INPUT NUM 1 =  $num1")
        delay(2000)

        Log.d(TAG, "doWork: INPUT NUM 2 = $num2")
        delay(5000)

        Log.d(TAG, "doWork: Finished")

        return Result.success(workDataOf("KEY_OUTPUT" to addResult))
    }

    companion object {
        private const val TAG = "MyWorker"
    }
}