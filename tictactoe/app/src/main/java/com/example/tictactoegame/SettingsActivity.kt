package com.example.tictactoegame

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat


class SettingsActivity : AppCompatActivity() {
    companion object {
        fun getSwitchValue(): String {
            return "BUTTON_SELECTED"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Settings"
        setSupportActionBar(toolbar)

        val window = this.window
        val prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val switchDarkMode: SwitchCompat = findViewById(R.id.switchDarkMode)

        switchDarkMode.isChecked = prefs.getBoolean(getSwitchValue(), false)

        if (switchDarkMode.isChecked) {
            toolbar.setBackgroundColor(getColor(R.color.colorPrimaryDarkDarkMode))
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDarkDarkMode)
        } else {
            toolbar.setBackgroundColor(getColor(R.color.colorPrimary))
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }

        switchDarkMode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                toolbar.setBackgroundColor(getColor(R.color.colorPrimaryDarkDarkMode))
                window.statusBarColor =
                    ContextCompat.getColor(this, R.color.colorPrimaryDarkDarkMode)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean(getSwitchValue(), true)
                editor.apply()
            } else {
                window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
                toolbar.setBackgroundColor(getColor(R.color.colorPrimary))
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean(getSwitchValue(), false)
                editor.apply()
            }
        }

    }
}