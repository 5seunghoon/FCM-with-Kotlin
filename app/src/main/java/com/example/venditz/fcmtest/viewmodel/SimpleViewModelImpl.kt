package com.example.venditz.fcmtest.viewmodel

import com.example.venditz.fcmtest.SimpleData
import io.reactivex.subjects.PublishSubject

class SimpleViewModelImpl : SimpleViewModel() {
    override fun addData(newData: SimpleData){
        simpleDataPublishSubject.onNext(newData)
        simpleDataList.add(newData)
    }
}
