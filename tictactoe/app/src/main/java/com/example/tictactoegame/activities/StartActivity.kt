package com.example.tictactoegame.activities

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.tictactoegame.R

class StartActivity : AppCompatActivity() {
    companion object {
        private lateinit var mediaPlayer: MediaPlayer

        fun getMediaPlayer(): MediaPlayer {
            return mediaPlayer
        }

        fun getValue(): String {
            return "GAME MODE"
        }
    }

    var length = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        mediaPlayer = MediaPlayer.create(this, R.raw.gamesong)
        mediaPlayer.isLooping = true
        mediaPlayer.setVolume(100F, 100F)

        val mainLayout = findViewById<ConstraintLayout>(R.id.startLayout)
        val prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean(SettingsActivity.getDarkModeSwitchValue(), false)
        val title = findViewById<TextView>(R.id.title)
        val playerButton = findViewById<Button>(R.id.startPlayer)
        val computerButton = findViewById<Button>(R.id.startComputer)
        val settingsButton = findViewById<Button>(R.id.settings)

        if (isDarkMode) {
            playerButton.setBackgroundResource(R.drawable.oval_white_button)
            playerButton.setTextColor(getColor(R.color.colorPrimaryDarkDarkMode))

            settingsButton.setBackgroundResource(R.drawable.oval_white_button)
            settingsButton.setTextColor(getColor(R.color.colorPrimaryDarkDarkMode))

            computerButton.setBackgroundResource(R.drawable.oval_white_button)
            computerButton.setTextColor(getColor(R.color.colorPrimaryDarkDarkMode))

            title.setTextColor(getColor(R.color.white))
            mainLayout.setBackgroundResource(R.color.colorPrimaryDarkDarkMode)
            window.statusBarColor = ContextCompat.getColor(
                this,
                R.color.colorPrimaryDarkDarkMode
            )
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            playerButton.setBackgroundResource(R.drawable.oval_purple_button)
            playerButton.setTextColor(getColor(R.color.white))

            settingsButton.setBackgroundResource(R.drawable.oval_purple_button)
            settingsButton.setTextColor(getColor(R.color.white))

            computerButton.setBackgroundResource(R.drawable.oval_white_button)
            computerButton.setTextColor(getColor(R.color.colorPrimaryDark))

            title.setTextColor(getColor(R.color.colorPrimaryDark))
            mainLayout.setBackgroundResource(R.color.colorSuperDuperLight)
            window.statusBarColor = ContextCompat.getColor(
                this,
                R.color.colorPrimaryDark
            )
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        mediaPlayer.start()
    }

    fun player(view: View) {
        val playerIntent = Intent(this, MainActivity::class.java)
        startActivity(playerIntent)
        finish()
    }

    fun computer(view: View) {
        val computerIntent = Intent(this, MainActivity::class.java)
        computerIntent.putExtra(getValue(), true)
        startActivity(computerIntent)
        finish()
    }

    fun settings(view: View) {
        val settingsIntent = Intent(this, SettingsActivity::class.java)
        startActivity(settingsIntent)
        finish()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!hasFocus) {
            mediaPlayer.pause()
            length = mediaPlayer.currentPosition
        } else {
            mediaPlayer.seekTo(length)
            mediaPlayer.start()
        }

    }
}