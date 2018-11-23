package com.example.venditz.fcmtest.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager
import com.example.venditz.fcmtest.Logger
import com.example.venditz.fcmtest.view.MainActivity
import com.example.venditz.fcmtest.R
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebaseMessagingServiceTest : FirebaseMessagingService() {
    private var isForeground = false

    private val TAG = "FCM_SERVICE_TEST"
    private val NOTIFICATION_ID = 0

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // 데이터 메세지가 여기서 처리됨
        // 노티피케이션 메세지는 포그라운드일때도 여기서 처리

        // Todo : Handle FCM message
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        isForeground = false

        Logger.d(TAG, "FCM FROM : ${remoteMessage?.from}")

        remoteMessage?.data?.let {
            if(it.isNotEmpty()){
                Logger.d(TAG, "DATA : ${remoteMessage.data}")
                if(isLongRunningJob()) {
                    scheduleJob()
                } else {
                    handleNow(remoteMessage.data)
                }
                isForeground = true
            }
        }

        if(!isForeground) {
            remoteMessage?.notification?.let {
                Logger.d(TAG, "msg noti body : ${it.body}")
                sendNotification(it.title ?: "TITLE", it.body ?: "BODY")
            }
        }
    }

    override fun onNewToken(token: String?) {
        // FCM InstanceID 토큰이 업데이트되면
        Logger.d(TAG, "Refreshed token: $token")

        sendRegistrationToServer(token)
    }

    private fun sendNotification(messageTitle:String, messageBody:String) {

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, MainActivity.REQUEST_CODE, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle(messageTitle)
            setContentText(messageBody)
            setAutoCancel(true)
            setSound(defaultSoundUri)
            setContentIntent(pendingIntent)
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun sendRegistrationToServer(token: String?) {
        // 서버로 새 토큰 보내주기
        token?.let {
            // TODO : send token to app server
        }
    }

    private fun handleNow(data: MutableMap<String, String>) {
        Logger.d(TAG, "HANDLE NOW")
        val intent = Intent(FcmConstant.broadcastIntentAction).apply {
            putExtra(FcmConstant.fcmTitleKey, data[FcmConstant.fcmTitleKey])
            putExtra(FcmConstant.fcmMessageKey, data[FcmConstant.fcmMessageKey])
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)

    }

    private fun scheduleJob() {
        // start dispatch_job
        val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))
        val myJob = dispatcher.newJobBuilder().apply {
            setService(FirebaseJobServiceTest::class.java)
            setTag("my-job-tag")
        }.build()
        dispatcher.schedule(myJob)
    }

    private fun isLongRunningJob(): Boolean {
        return false
    }

}
