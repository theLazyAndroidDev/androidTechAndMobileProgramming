package com.example.project12

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder
import androidx.core.app.NotificationCompat

class RingtoneService : Service() {

        var AlarmUri : Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        var rin = RingtoneManager.getRingtone(baseContext, AlarmUri)

    var id = 0
    var isRunning : Boolean = false

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        var state = intent!!.getStringExtra("extra")
assert(state!=null)
        when(state){
            "on" -> id = 1
            "off" -> id = 0
        }

        if(!this.isRunning && id === 1){
            playAlarm()
            this.isRunning = true
            this.id = 0
        }
        else if(this.isRunning && id === 0){
            rin.stop()
            this.isRunning = false
            this.id = 0
        }
        else if(!this.isRunning && id === 0){
            this.isRunning = false
            this.id = 0
        }
        else if(this.isRunning && id === 1){
            this.isRunning = true
            this.id = 1
        }
        else{

        }

        return START_NOT_STICKY
    }

    private fun playAlarm() {

        if (AlarmUri === null){
            AlarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        rin = RingtoneManager.getRingtone(baseContext, AlarmUri)
        rin.play()

    }

    override fun onDestroy() {
        super.onDestroy()
        this.isRunning = false
    }

}