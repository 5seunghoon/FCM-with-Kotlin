package com.example.venditz.fcmtest

import android.util.Log

object Logger {
    private val TAG = "[LOGGER_TAG]"
    fun d(str:String){
        Log.d(TAG, str)
    }
    fun d(tag:String, str:String) {
        Log.d("$TAG tag", str)
    }
}