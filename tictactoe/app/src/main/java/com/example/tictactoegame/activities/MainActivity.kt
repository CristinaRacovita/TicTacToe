package com.example.tictactoegame.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.tictactoegame.R
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private var player1 = ArrayList<Int>()
    private var player2 = ArrayList<Int>()
    private var activePlayer = 1
    private var playWithComputer = false
    private var button1: Button? = null
    private var button2: Button? = null
    private var button3: Button? = null
    private var button4: Button? = null
    private var button5: Button? = null
    private var button6: Button? = null
    private var button7: Button? = null
    private var button8: Button? = null
    private var button9: Button? = null
    private var length = 0
    private lateinit var prefs: SharedPreferences
    private var isMusicOn = false
    private var case = 0
    private var isOk = true

    private var allButtonsAvailable = mutableMapOf(
        R.id.button1 to 1,
        R.id.button2 to 2,
        R.id.button3 to 3,
        R.id.button4 to 4,
        R.id.button5 to 5,
        R.id.button6 to 6,
        R.id.button7 to 7,
        R.id.button8 to 8,
        R.id.button9 to 9
    )

    override fun onBackPressed() {
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        isMusicOn = prefs.getBoolean(SettingsActivity.getMusicSwitchValue(), true)
        case = prefs.getInt(SettingsActivity.getGameMode(), 1)

        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)
        button7 = findViewById(R.id.button7)
        button8 = findViewById(R.id.button8)
        button9 = findViewById(R.id.button9)

        val prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean(SettingsActivity.getDarkModeSwitchValue(), false)
        val mainLayout = findViewById<ConstraintLayout>(R.id.mainLayout)
        val title = findViewById<TextView>(R.id.title1)
        val player1 = findViewById<TextView>(R.id.player1)
        val player2 = findViewById<TextView>(R.id.player2)
        val scorePlayer1 = findViewById<TextView>(R.id.player1Score)
        val scorePlayer2 = findViewById<TextView>(R.id.player2Score)
        val table = findViewById<TableLayout>(R.id.table)

        if (isDarkMode) {
            window.statusBarColor = ContextCompat.getColor(
                this,
                R.color.colorPrimaryDarkDarkMode
            )

            table.setBackgroundResource(R.color.colorPrimaryDarkDarkMode)
            mainLayout.setBackgroundResource(R.color.colorPrimaryDarkDarkMode)
            title.setTextColor(getColor(R.color.white))
            player1.setTextColor(getColor(R.color.white))
            player2.setTextColor(getColor(R.color.white))
            scorePlayer1.setTextColor(getColor(R.color.white))
            scorePlayer2.setTextColor(getColor(R.color.white))

            button1?.setBackgroundResource(R.color.white)
            button2?.setBackgroundResource(R.color.white)
            button3?.setBackgroundResource(R.color.white)
            button4?.setBackgroundResource(R.color.white)
            button5?.setBackgroundResource(R.color.white)
            button6?.setBackgroundResource(R.color.white)
            button7?.setBackgroundResource(R.color.white)
            button8?.setBackgroundResource(R.color.white)
            button9?.setBackgroundResource(R.color.white)

        } else {
            window.statusBarColor = ContextCompat.getColor(
                this,
                R.color.colorPrimaryDark
            )

            table.setBackgroundResource(R.color.white)
            mainLayout.setBackgroundResource(R.color.white)
            title.setTextColor(getColor(R.color.colorPrimaryDark))
            player1.setTextColor(getColor(R.color.colorPrimaryDark))
            player2.setTextColor(getColor(R.color.colorPrimaryDark))
            scorePlayer1.setTextColor(getColor(R.color.colorPrimaryDark))
            scorePlayer2.setTextColor(getColor(R.color.colorPrimaryDark))

            button1?.setBackgroundResource(R.color.colorPrimaryLight)
            button2?.setBackgroundResource(R.color.colorPrimaryLight)
            button3?.setBackgroundResource(R.color.colorPrimaryLight)
            button4?.setBackgroundResource(R.color.colorPrimaryLight)
            button5?.setBackgroundResource(R.color.colorPrimaryLight)
            button6?.setBackgroundResource(R.color.colorPrimaryLight)
            button7?.setBackgroundResource(R.color.colorPrimaryLight)
            button8?.setBackgroundResource(R.color.colorPrimaryLight)
            button9?.setBackgroundResource(R.color.colorPrimaryLight)
        }

        playWithComputer = intent.getBooleanExtra(StartActivity.getValue(), false)
        if (playWithComputer) {
            val player2Text: TextView = findViewById(R.id.player2)
            player2Text.text = getString(R.string.computer)
        }

        val scorePlayer2TextView: TextView = findViewById(R.id.player2Score)
        val scorePlayer1TextView: TextView = findViewById(R.id.player1Score)

        scorePlayer1TextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (!checkNextGame()) {
                    val score1 = scorePlayer1TextView.text.toString().toInt()
                    val score2 = scorePlayer2TextView.text.toString().toInt()

                    var message = ""
                    if (score1 > score2) {
                        message = "Player1 WIIIIIIN"
                    } else {
                        message = "Player2 WIIIIIIN"
                    }
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("Congrats!")
                        .setMessage(message)
                        .setPositiveButton(
                            R.string.playAgain
                        ) { dialog, which ->
                            isOk = true
                            newGame()
                            scorePlayer1TextView.text = getString(R.string.startScore)
                            scorePlayer2TextView.text = getString(R.string.startScore)
                        }
                        .setNegativeButton(R.string.goMainPage) { dialog, which ->
                            val intent = Intent(applicationContext, StartActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .show()
                    isOk = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        scorePlayer2TextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (!checkNextGame()) {
                    isOk = false
                    val score1 = scorePlayer1TextView.text.toString().toInt()
                    val score2 = scorePlayer2TextView.text.toString().toInt()

                    var message = ""
                    if (score1 > score2) {
                        message = "Player1 WIIIIIIN"
                    } else {
                        message = "Player2 WIIIIIIN"
                    }
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("Congrats!")
                        .setMessage(message)
                        .setPositiveButton(
                            R.string.playAgain
                        ) { dialog, which ->
                            isOk = true
                            newGame()
                            scorePlayer1TextView.text = getString(R.string.startScore)
                            scorePlayer2TextView.text = getString(R.string.startScore)
                        }
                        .setNegativeButton(R.string.goMainPage) { dialog, which ->
                            val intent = Intent(applicationContext, StartActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .show()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


    }

    fun pressButton(view: View) {
        if (isOk) {
            playGame(view as Button)
        }
    }

    private fun playComputer(view: Button) {
        allButtonsAvailable[view.id]?.let { player1.add(it) }
        allButtonsAvailable.remove(view.id)
        view.text = "X"
        view.setBackgroundColor(getColor(R.color.colorAccent))

        if (allButtonsAvailable.isNotEmpty()) {

            val random = Random()
            val randomEntry =
                allButtonsAvailable.entries.elementAt(random.nextInt(allButtonsAvailable.size))
            val newButtonSelected: Button = findViewById(randomEntry.key)

            newButtonSelected.text = "0"
            newButtonSelected.setBackgroundColor(getColor(R.color.colorPrimary))
            player2.add(randomEntry.value)

            allButtonsAvailable.remove(randomEntry.key)

            view.isEnabled = false
            newButtonSelected.isEnabled = false

        }

        checkWinner()

    }

    private fun playGame(view: Button) {
        if (playWithComputer) {
            playComputer(view)
        } else {
            playPlayer(view)
        }
    }

    private fun playPlayer(view: Button) {
        if (activePlayer == 1) {
            view.text = "X"
            view.setBackgroundColor(getColor(R.color.colorAccent))

            allButtonsAvailable[view.id]?.let { player1.add(it) }
            activePlayer = 2
        } else {
            view.text = "0"
            view.setBackgroundColor(getColor(R.color.colorPrimary))

            allButtonsAvailable[view.id]?.let { player2.add(it) }
            activePlayer = 1
        }

        view.isEnabled = false
        checkWinner()
    }

    private fun checkNextGame(): Boolean {
        val scorePlayer2TextView: TextView = findViewById(R.id.player2Score)
        val scorePlayer1TextView: TextView = findViewById(R.id.player1Score)

        val sum =
            scorePlayer1TextView.text.toString().toInt() + scorePlayer2TextView.text.toString()
                .toInt()

        when (case) {
            1 -> if (sum == 3) {
                return false
            }
            2 -> if (sum == 9) {
                return false
            }
            3 -> if (sum == 15) {
                return false
            }
        }
        return true
    }

    private fun checkWinner() {
        var winner = -1

        if (player1.contains(1) && player1.contains(2) && player1.contains(3)) {
            winner = 1
        }
        if (player2.contains(1) && player2.contains(2) && player2.contains(3)) {
            winner = 2
        }

        if (player1.contains(4) && player1.contains(5) && player1.contains(6)) {
            winner = 1
        }
        if (player2.contains(4) && player2.contains(5) && player2.contains(6)) {
            winner = 2
        }

        if (player1.contains(7) && player1.contains(8) && player1.contains(9)) {
            winner = 1
        }
        if (player2.contains(7) && player2.contains(8) && player2.contains(9)) {
            winner = 2
        }

        if (player1.contains(1) && player1.contains(4) && player1.contains(7)) {
            winner = 1
        }
        if (player2.contains(1) && player2.contains(4) && player2.contains(7)) {
            winner = 2
        }

        if (player1.contains(2) && player1.contains(5) && player1.contains(8)) {
            winner = 1
        }
        if (player2.contains(2) && player2.contains(5) && player2.contains(8)) {
            winner = 2
        }

        if (player1.contains(3) && player1.contains(6) && player1.contains(9)) {
            winner = 1
        }
        if (player2.contains(3) && player2.contains(6) && player2.contains(9)) {
            winner = 2
        }

        if (player1.contains(1) && player1.contains(5) && player1.contains(9)) {
            winner = 1
        }
        if (player2.contains(1) && player2.contains(5) && player2.contains(9)) {
            winner = 2
        }

        if (player1.contains(3) && player1.contains(5) && player1.contains(7)) {
            winner = 1
        }
        if (player2.contains(3) && player2.contains(5) && player2.contains(7)) {
            winner = 2
        }

        if (winner != -1) {
            if (winner == 1) {
                Toast.makeText(this, " Player 1  win the game", Toast.LENGTH_LONG).show()
                val scorePlayer1TextView: TextView = findViewById(R.id.player1Score)
                var scorePlayer1 = scorePlayer1TextView.text.toString().toInt()
                scorePlayer1 += 1
                scorePlayer1TextView.text = "$scorePlayer1"
            } else {
                Toast.makeText(this, " Player 2  win the game", Toast.LENGTH_LONG).show()
                val scorePlayer2TextView: TextView = findViewById(R.id.player2Score)
                var scorePlayer2 = scorePlayer2TextView.text.toString().toInt()
                scorePlayer2 += 1
                scorePlayer2TextView.text = "$scorePlayer2"
            }

            newGame()

        } else {
            if ((player1.size == 4 && player2.size == 5) || (player1.size == 5 && player2.size == 4)) {
                Toast.makeText(this, "It's a tie", Toast.LENGTH_LONG).show()
                newGame()
            }
        }
    }

    private fun newGame() {

        button1?.setBackgroundColor(getColor(R.color.colorPrimaryLight))
        button1?.text = ""
        button1?.isEnabled = true

        button2?.setBackgroundColor(getColor(R.color.colorPrimaryLight))
        button2?.text = ""
        button2?.isEnabled = true

        button3?.setBackgroundColor(getColor(R.color.colorPrimaryLight))
        button3?.text = ""
        button3?.isEnabled = true

        button4?.setBackgroundColor(getColor(R.color.colorPrimaryLight))
        button4?.text = ""
        button4?.isEnabled = true

        button5?.setBackgroundColor(getColor(R.color.colorPrimaryLight))
        button5?.text = ""
        button5?.isEnabled = true

        button6?.setBackgroundColor(getColor(R.color.colorPrimaryLight))
        button6?.text = ""
        button6?.isEnabled = true

        button7?.setBackgroundColor(getColor(R.color.colorPrimaryLight))
        button7?.text = ""
        button7?.isEnabled = true

        button8?.setBackgroundColor(getColor(R.color.colorPrimaryLight))
        button8?.text = ""
        button8?.isEnabled = true

        button9?.setBackgroundColor(getColor(R.color.colorPrimaryLight))
        button9?.text = ""
        button9?.isEnabled = true

        allButtonsAvailable = mutableMapOf(
            R.id.button1 to 1,
            R.id.button2 to 2,
            R.id.button3 to 3,
            R.id.button4 to 4,
            R.id.button5 to 5,
            R.id.button6 to 6,
            R.id.button7 to 7,
            R.id.button8 to 8,
            R.id.button9 to 9
        )

        player1.clear()
        player2.clear()
        activePlayer = 1

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        val mediaPlayer = StartActivity.getMediaPlayer()

        if (isMusicOn) {
            if (!hasFocus) {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.pause()
                    length = mediaPlayer.currentPosition
                }
            } else {
                mediaPlayer.seekTo(length)
                mediaPlayer.start()
            }
        }

    }


}