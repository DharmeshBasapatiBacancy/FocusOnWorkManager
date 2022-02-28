package com.example.focusonworkmanager

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.example.focusonworkmanager.databinding.ActivityMainBinding
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartWork.setOnClickListener {
            startOneTimeWork()
        }

        binding.btnStartTimeLoggerWork.setOnClickListener {
            startTimeLoggerWork()
        }

    }

    private fun startTimeLoggerWork() {
        Log.d(TAG, "startTimeLoggerWork: Work Initialized")
        val workRequest = PeriodicWorkRequestBuilder<TimeLoggerWorker>(15, TimeUnit.MINUTES)
            .addTag("TimeLoggerWorker")
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
        observerTimeLoggerWork(workRequest.id)
    }

    private fun observerTimeLoggerWork(workerId: UUID) {
        val workStatus = WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(workerId)
        workStatus.observe(this) {
            Log.d(TAG, "observerTimeLoggerWork: ${it?.state}")
        }
    }

    private fun startOneTimeWork() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        Log.d(TAG, "startOneTimeWork: Work Initialized")
        val workRequest = OneTimeWorkRequestBuilder<MyWorker>()
            .addTag("MyWorker")
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .setInputData(workDataOf("KEY_ONE" to 65, "KEY_TWO" to 72))
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
        observerWork(workRequest.id)
    }

    private fun observerWork(workerId: UUID) {
        val workStatus = WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(workerId)
        workStatus.observe(this) {
            Log.d(TAG, "observerWork: ${it?.state}")
            if (it?.state?.isFinished!!) {
                Log.d(TAG, "observerWork: ADD RESULT = ${it.outputData.getInt("KEY_OUTPUT", 0)}")
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}