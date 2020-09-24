package com.example.tictactoegame

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    fun player(view: View) {
        val playerIntent = Intent(this, MainActivity::class.java)
        startActivity(playerIntent)
    }

    fun computer(view: View) {

    }

    fun settings(view: View) {

    }
}