package com.bkex.calendarandalarmdemo

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.bkex.calendarandalarmdemo.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*

private const val TAG = "ALARM-TEST"

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab).setAction("Action", null).show()
        }
        binding.contentLayout.timeAddedBtn.setOnClickListener {
            var time = binding.contentLayout.timeEditText.text.toString()
            testAlarm(time)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun testAlarm(time: String) {
        if (time.isNullOrBlank()) {
            return
        }

        val timeDivider = time.split(":")
        if (timeDivider.size != 2) {
            return
        }

        val hour = timeDivider[0]
        val minute = timeDivider[1]

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, hour.toInt())
        calendar.set(Calendar.MINUTE, minute.toInt())
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        var alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP, calendar.timeInMillis, "", {
                    Log.d(TAG, "#testAlarm# 闹钟时间到：$time")
                    Toast.makeText(applicationContext, "时间到：$time", Toast.LENGTH_LONG).show()
                }, Handler(Looper.getMainLooper())
            )
            Log.d(TAG, "闹钟添加成功: $time")
            Toast.makeText(applicationContext, "闹钟添加成功: $time", Toast.LENGTH_LONG).show()
        }

    }
}