package com.example.venditz.fcmtest.di

import com.example.venditz.fcmtest.SimpleAdapter
import com.example.venditz.fcmtest.viewmodel.SimpleViewModel
import com.example.venditz.fcmtest.viewmodel.SimpleViewModelImpl
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val MyAppModule = module {
    factory {
        SimpleAdapter()
    }
    viewModel {
        SimpleViewModelImpl() as SimpleViewModel
    }
}

val AppModule = listOf(MyAppModule)