package com.lyz.lyzwanandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * @author mac
 * @create 2021/01/07
 * @Describe
 */

class SplashActivity : AppCompatActivity() {

    private var schedule: ScheduledThreadPoolExecutor? = null

    private var task: Runnable? = Runnable {
        goMain()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        schedule = ScheduledThreadPoolExecutor(1)
        schedule!!.schedule(task, 2000L, TimeUnit.MILLISECONDS)
    }

    private fun goMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        schedule?.remove(task)
        super.onBackPressed()
    }

}