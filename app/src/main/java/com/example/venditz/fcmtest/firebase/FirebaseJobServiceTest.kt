package com.example.venditz.fcmtest.firebase

import android.util.Log
import com.example.venditz.fcmtest.Logger
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService

class FirebaseJobServiceTest : JobService (){
    private val TAG = "FIREBASE_JOB_SERVICE"
    override fun onStopJob(job: JobParameters?): Boolean {
        Logger.d(TAG, "job service stop")
        return false
    }

    override fun onStartJob(job: JobParameters?): Boolean {
        Logger.d(TAG, "job service start")
        return false
    }

}