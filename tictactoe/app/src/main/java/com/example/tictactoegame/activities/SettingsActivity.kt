package com.example.tictactoegame.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.tictactoegame.R


class SettingsActivity : AppCompatActivity() {
    companion object {
        fun getDarkModeSwitchValue(): String {
            return "BUTTON_SELECTED"
        }
    }

    private lateinit var prefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Settings"
        setSupportActionBar(toolbar)

        prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        editor = prefs.edit()

        checkDarkMode()
        checkMusic()
    }

    override fun onBackPressed() {
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        finish()
        StartActivity.getMediaPlayer().pause()
    }

    private fun checkDarkMode() {
        val switchDarkMode: SwitchCompat = findViewById(R.id.switchDarkMode)

        switchDarkMode.isChecked = prefs.getBoolean(getDarkModeSwitchValue(), false)

        if (switchDarkMode.isChecked) {
            toolbar.setBackgroundColor(getColor(R.color.colorPrimaryDarkDarkMode))
            window.statusBarColor = ContextCompat.getColor(
                this,
                R.color.colorPrimaryDarkDarkMode
            )
        } else {
            toolbar.setBackgroundColor(getColor(R.color.colorPrimary))
            window.statusBarColor = ContextCompat.getColor(
                this,
                R.color.colorPrimaryDark
            )
        }

        switchDarkMode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                toolbar.setBackgroundColor(getColor(R.color.colorPrimaryDarkDarkMode))
                window.statusBarColor =
                    ContextCompat.getColor(
                        this,
                        R.color.colorPrimaryDarkDarkMode
                    )
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean(getDarkModeSwitchValue(), true)
                editor.apply()
            } else {
                window.statusBarColor = ContextCompat.getColor(
                    this,
                    R.color.colorPrimaryDark
                )
                toolbar.setBackgroundColor(getColor(R.color.colorPrimary))
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean(getDarkModeSwitchValue(), false)
                editor.apply()
            }
        }
    }

    fun checkMusic() {
        val switchAudio = findViewById<SwitchCompat>(R.id.switchMusic)
        val musicSwitchValue = "MUSIC_MODE"
        switchAudio.isChecked = prefs.getBoolean(musicSwitchValue, false)

        if (switchAudio.isChecked) {
            StartActivity.getMediaPlayer().start()
        } else {
            StartActivity.getMediaPlayer().pause()
        }

        switchAudio.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                StartActivity.getMediaPlayer().start()
                editor.putBoolean(musicSwitchValue, true)
                editor.apply()
            } else {
                StartActivity.getMediaPlayer().pause()
                editor.putBoolean(musicSwitchValue, false)
                editor.apply()
            }
        }
    }
}