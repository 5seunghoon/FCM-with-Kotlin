package com.example.venditz.fcmtest.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.venditz.fcmtest.*
import com.example.venditz.fcmtest.firebase.FcmConstant
import com.example.venditz.fcmtest.viewmodel.SimpleViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {
    companion object {
        const val REQUEST_CODE = 101
    }

    private val simpleViewModel: SimpleViewModel by viewModel()
    private val simpleAdapter: SimpleAdapter by inject()

    private var broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val title = intent?.getStringExtra(FcmConstant.fcmTitleKey) ?: "ERROR : NO TITLE"
            val message = intent?.getStringExtra(FcmConstant.fcmMessageKey) ?: "ERROR : NO MESSAGE"
            simpleViewModel.addData(SimpleData(title, message))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // recycler view setting
        simple_data_recycler_view.run {
            adapter = simpleAdapter
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
        }

        // simple data에 subscribe -> adpater에 data를 추가
        addDisposable(simpleViewModel.simpleDataPublishSubject.subscribe {
            main_none_text_view.let { v -> if (v.visibility == View.VISIBLE) v.visibility = View.GONE }
            simpleAdapter.addData(SimpleData(it.title, it.message), true)
        })

        // FCM 서비스로 부터 들어오는 데이터를 받기 위한 로컬 브로드캐스트 리시버
        LocalBroadcastManager.getInstance(this).registerReceiver(
            broadcastReceiver, IntentFilter(FcmConstant.broadcastIntentAction)
        )
    }
}
