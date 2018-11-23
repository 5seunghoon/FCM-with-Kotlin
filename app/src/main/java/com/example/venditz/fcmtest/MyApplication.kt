package com.example.venditz.fcmtest

import android.app.Application
import com.example.venditz.fcmtest.di.AppModule
import org.koin.android.ext.android.startKoin

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext, AppModule)
    }
}