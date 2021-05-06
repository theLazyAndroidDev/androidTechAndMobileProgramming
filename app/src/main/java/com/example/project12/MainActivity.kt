package com.example.project12

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_alarm.*
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var am: AlarmManager
    lateinit var tp: TimePicker
    lateinit var textt: TextView
    lateinit var btnStart: Button
    lateinit var btnStop: Button
    var hour = 0
    var min = 0
    lateinit var con: Context

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)

    this.con = this
        am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        tp = findViewById(R.id.timePicker) as TimePicker
        btnStart = findViewById(R.id.save) as Button
        btnStop = findViewById(R.id.stop) as Button
        var calendar : Calendar = Calendar.getInstance()
        var myIntent = Intent(this, AlarmReceiver::class.java)
        var dtbtn = findViewById<Switch>(R.id.DarkTheme)

       var frag = alarmFr()

        fragLO.removeAllViews()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragLO, frag, "s")
            .commit()

               btnStart.setOnClickListener{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        calendar.set(Calendar.HOUR_OF_DAY, tp.hour)
                        calendar.set(Calendar.MINUTE, tp.minute)
                        calendar.set(Calendar.SECOND, 0)
                        calendar.set(Calendar.MILLISECOND, 0)
                        hour = tp.hour
                        min = tp.minute

                    }
                    else{
                        calendar.set(Calendar.HOUR_OF_DAY, tp.currentHour)
                        calendar.set(Calendar.MINUTE, tp.currentMinute)
                        calendar.set(Calendar.SECOND, 0)
                        calendar.set(Calendar.MILLISECOND, 0)
                        hour = tp.currentHour
                        min = tp.currentMinute
                    }


                    var hrstr = hour.toString()
                var minstr = min.toString()

                    if (min < 10){minstr = "0$min"}
                    texttFr.text = " Alarm set to $hrstr : $minstr"
                    myIntent.putExtra("extra", "on")
                    var pi = PendingIntent.getBroadcast(this@MainActivity, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                    am.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,pi)
                }

        btnStop.setOnClickListener(){
            texttFr.text = "OFF"
            var pi = PendingIntent.getBroadcast(this@MainActivity, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            am.cancel(pi)
            myIntent.putExtra("extra", "off")
            sendBroadcast(myIntent)
        }

///////dark theme//////////////////////////////////////////////////
        dtbtn.setOnCheckedChangeListener{ buttonView, isChecked ->
if(dtbtn.isChecked()){
    tp.setBackgroundColor(R.color.black)
    bgrnd.setBackgroundColor(R.color.black)
    stop.setBackgroundColor(R.color.btnAlt)
    save.setBackgroundColor(R.color.btnAlt)
}
else{
    tp.setBackgroundColor(R.color.norm2)
    bgrnd.setBackgroundColor(R.color.norm2)
    stop.setBackgroundColor(R.color.black)
    save.setBackgroundColor(R.color.black)
}
        }
///////////////////////////////////////////////////////////////////
    }

}