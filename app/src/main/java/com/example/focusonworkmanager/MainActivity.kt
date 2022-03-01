package com.example.focusonworkmanager

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.example.focusonworkmanager.api_workers.GetProductsWorker
import com.example.focusonworkmanager.api_workers.GetUsersWorker
import com.example.focusonworkmanager.chain_workers.CreateAccountWorker
import com.example.focusonworkmanager.chain_workers.ExploreProductsWorker
import com.example.focusonworkmanager.chain_workers.LoginWorker
import com.example.focusonworkmanager.chain_workers.UpdateProfileWorker
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

        binding.btnStartChainWork.setOnClickListener {
            startChainWork()
        }

        binding.btnStartApiWork.setOnClickListener {
            startApiWork()
        }
    }

    private fun startApiWork() {

        val getProductsWorkRequest = OneTimeWorkRequestBuilder<GetProductsWorker>()
            .addTag("GetProductsWorker").build()

        val getUsersWorkRequest = OneTimeWorkRequestBuilder<GetUsersWorker>()
            .addTag("GetUsersWorker").build()

        WorkManager.getInstance(this)
            .beginWith(getProductsWorkRequest)
            .then(getUsersWorkRequest)
            .enqueue()

        WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(getProductsWorkRequest.id)
            .observe(this) {
                Log.d(TAG, "getProductsWorkRequest: ${it?.state}")
            }

        WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(getUsersWorkRequest.id)
            .observe(this) {
                Log.d(TAG, "getUsersWorkRequest: ${it?.state}")
            }

    }

    private fun startChainWork() {

        val createAccountRequest = OneTimeWorkRequestBuilder<CreateAccountWorker>()
            .addTag("CreateAccountWorker")
            .build()

        val loginRequest = OneTimeWorkRequestBuilder<LoginWorker>()
            .addTag("LoginWorker")
            .build()

        val updateProfileRequest = OneTimeWorkRequestBuilder<UpdateProfileWorker>()
            .addTag("UpdateProfileWorker")
            .build()

        val exploreProductsRequest = OneTimeWorkRequestBuilder<ExploreProductsWorker>()
            .addTag("ExploreProductsWorker")
            .build()

        val workManager = WorkManager.getInstance(this)

        /*workManager.beginWith(createAccountRequest)
            .then(loginRequest)
            .then(updateProfileRequest)
            .then(exploreProductsRequest)
            .enqueue()*/

        workManager.beginWith(listOf(createAccountRequest, loginRequest))
            .then(updateProfileRequest)
            .then(exploreProductsRequest)
            .enqueue()

        observeChainWorkers(createAccountRequest.id)
        observeChainWorkers(loginRequest.id)
        observeChainWorkers(updateProfileRequest.id)
        observeChainWorkers(exploreProductsRequest.id)

    }

    private fun observeChainWorkers(workerId: UUID) {
        val workStatus = WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(workerId)
        workStatus.observe(this) {
            Log.d(TAG, "observeChainWorkers: ${it?.tags}")
            Log.d(TAG, "observeChainWorkers: ${it?.state}")
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