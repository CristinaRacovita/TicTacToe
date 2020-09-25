package com.example.tictactoegame.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
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

        fun getMusicSwitchValue(): String {
            return "MUSIC_MODE"
        }

        fun getGameMode(): String {
            return "GAME_MODE"
        }
    }

    private lateinit var prefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var toolbar: Toolbar
    private val radioMap = mapOf(1 to R.id.firstCase, 2 to R.id.secondCase, 3 to R.id.thirdCase)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Settings"
        setSupportActionBar(toolbar)

        prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        editor = prefs.edit()

        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)

        radioMap[prefs.getInt(getGameMode(), 1)]?.let { radioGroup.check(it) }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            editor.putInt(getGameMode(), radioGroup.indexOfChild(findViewById(checkedId))+1)
            editor.apply()
        }

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
        editor = prefs.edit()

        val switchDarkMode: SwitchCompat = findViewById(R.id.switchDarkMode)
        val switchMusicMode: SwitchCompat = findViewById(R.id.switchMusic)
        val gameMode: TextView = findViewById(R.id.gameMode)
        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        val firstCase: RadioButton = findViewById(R.id.firstCase)
        val secondCase: RadioButton = findViewById(R.id.secondCase)
        val thirdCase: RadioButton = findViewById(R.id.thirdCase)

        switchDarkMode.isChecked = prefs.getBoolean(getDarkModeSwitchValue(), false)

        if (switchDarkMode.isChecked) {
            switchDarkMode.setTextColor(getColor(R.color.colorPrimaryDarkDarkMode))
            switchMusicMode.setTextColor(getColor(R.color.colorPrimaryDarkDarkMode))
            switchDarkMode.setBackgroundResource(R.drawable.oval_white_button)
            switchMusicMode.setBackgroundResource(R.drawable.oval_white_button)

            gameMode.setTextColor(getColor(R.color.white))
            radioGroup.setBackgroundResource(R.drawable.oval_white_button)
            firstCase.setTextColor(getColor(R.color.colorPrimaryDarkDarkMode))
            secondCase.setTextColor(getColor(R.color.colorPrimaryDarkDarkMode))
            thirdCase.setTextColor(getColor(R.color.colorPrimaryDarkDarkMode))

            toolbar.setBackgroundColor(getColor(R.color.colorPrimaryDarkDarkMode))
            window.statusBarColor = ContextCompat.getColor(
                this,
                R.color.colorPrimaryDarkDarkMode
            )
        } else {
            switchDarkMode.setTextColor(getColor(R.color.colorPrimaryDark))
            switchMusicMode.setTextColor(getColor(R.color.colorPrimaryDark))
            switchDarkMode.setBackgroundResource(R.drawable.oval_background)
            switchMusicMode.setBackgroundResource(R.drawable.oval_background)

            gameMode.setTextColor(getColor(R.color.colorPrimaryDark))
            radioGroup.setBackgroundResource(R.drawable.oval_background)
            firstCase.setTextColor(getColor(R.color.colorPrimaryDark))
            secondCase.setTextColor(getColor(R.color.colorPrimaryDark))
            thirdCase.setTextColor(getColor(R.color.colorPrimaryDark))

            toolbar.setBackgroundColor(getColor(R.color.colorPrimary))
            window.statusBarColor = ContextCompat.getColor(
                this,
                R.color.colorPrimaryDark
            )
        }

        switchDarkMode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switchDarkMode.setTextColor(getColor(R.color.white))
                switchMusicMode.setTextColor(getColor(R.color.white))
                switchDarkMode.setBackgroundResource(R.drawable.oval_white_button)
                switchMusicMode.setBackgroundResource(R.drawable.oval_white_button)

                gameMode.setTextColor(getColor(R.color.white))
                radioGroup.setBackgroundResource(R.drawable.oval_white_button)
                firstCase.setTextColor(getColor(R.color.colorPrimaryDarkDarkMode))
                secondCase.setTextColor(getColor(R.color.colorPrimaryDarkDarkMode))
                thirdCase.setTextColor(getColor(R.color.colorPrimaryDarkDarkMode))

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
                switchDarkMode.setTextColor(getColor(R.color.colorPrimaryDark))
                switchMusicMode.setTextColor(getColor(R.color.colorPrimaryDark))
                switchDarkMode.setBackgroundResource(R.drawable.oval_background)
                switchMusicMode.setBackgroundResource(R.drawable.oval_background)

                gameMode.setTextColor(getColor(R.color.colorPrimaryDark))
                radioGroup.setBackgroundResource(R.drawable.oval_background)
                firstCase.setTextColor(getColor(R.color.colorPrimaryDark))
                secondCase.setTextColor(getColor(R.color.colorPrimaryDark))
                thirdCase.setTextColor(getColor(R.color.colorPrimaryDark))

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

    private fun checkMusic() {
        editor = prefs.edit()

        val switchAudio = findViewById<SwitchCompat>(R.id.switchMusic)
        switchAudio.isChecked = prefs.getBoolean(getMusicSwitchValue(), true)

        if (switchAudio.isChecked) {
            StartActivity.getMediaPlayer().start()
        } else {
            if (StartActivity.getMediaPlayer().isPlaying) {
                StartActivity.getMediaPlayer().pause()
            }
        }

        switchAudio.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                StartActivity.getMediaPlayer().start()
                editor.putBoolean(getMusicSwitchValue(), true)
                editor.apply()
            } else {
                if (StartActivity.getMediaPlayer().isPlaying) {
                    StartActivity.getMediaPlayer().pause()
                    editor.putBoolean(getMusicSwitchValue(), false)
                    editor.apply()
                }
            }
        }
    }
}