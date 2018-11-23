package com.example.venditz.fcmtest.viewmodel

import android.arch.lifecycle.ViewModel
import com.example.venditz.fcmtest.SimpleData
import io.reactivex.subjects.PublishSubject

abstract class SimpleViewModel : ViewModel() {
    var simpleDataPublishSubject: PublishSubject<SimpleData> = PublishSubject.create()

    internal var simpleDataList:MutableList<SimpleData> = mutableListOf()

    abstract fun addData(newData: SimpleData)
}