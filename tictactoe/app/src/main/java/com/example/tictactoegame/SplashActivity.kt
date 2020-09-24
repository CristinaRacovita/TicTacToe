package com.example.tictactoegame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashTimeMilliseconds: Long = 2000
        Handler().postDelayed({
            val startIntent = Intent(this@SplashActivity, StartActivity::class.java)
            startActivity(startIntent)
            finish()
        }, splashTimeMilliseconds)

    }
}
