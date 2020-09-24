package com.example.tictactoegame

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var player1 = ArrayList<Int>()
    private var player2 = ArrayList<Int>()
    private var activePlayer = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun pressButton(view: View) {
        var cellID = 0
        when (view.id) {
            R.id.button1 -> cellID = 1
            R.id.button2 -> cellID = 2
            R.id.button3 -> cellID = 3
            R.id.button4 -> cellID = 4
            R.id.button5 -> cellID = 5
            R.id.button6 -> cellID = 6
            R.id.button7 -> cellID = 7
            R.id.button8 -> cellID = 8
            R.id.button9 -> cellID = 9
        }

        playGame(cellID, view as Button)

    }

    private fun playGame(cellId: Int, view: Button) {
        if (activePlayer == 1) {
            view.text = "X"
            view.setBackgroundColor(getColor(R.color.colorAccent))

            player1.add(cellId)
            activePlayer = 2
        } else {
            view.text = "0"
            view.setBackgroundColor(getColor(R.color.colorPrimary))

            player2.add(cellId)
            activePlayer = 1
        }

        view.isEnabled = false

        checkWinner()
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

        }
    }

    private fun newGame() {
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)

        button1.setBackgroundColor(getColor(R.color.blueLight))
        button1.text = ""
        button1.isEnabled = true

        button2.setBackgroundColor(getColor(R.color.blueLight))
        button2.text = ""
        button2.isEnabled = true

        button3.setBackgroundColor(getColor(R.color.blueLight))
        button3.text = ""
        button3.isEnabled = true

        button4.setBackgroundColor(getColor(R.color.blueLight))
        button4.text = ""
        button4.isEnabled = true

        button5.setBackgroundColor(getColor(R.color.blueLight))
        button5.text = ""
        button5.isEnabled = true

        button6.setBackgroundColor(getColor(R.color.blueLight))
        button6.text = ""
        button6.isEnabled = true

        button7.setBackgroundColor(getColor(R.color.blueLight))
        button7.text = ""
        button7.isEnabled = true

        button8.setBackgroundColor(getColor(R.color.blueLight))
        button8.text = ""
        button8.isEnabled = true

        button9.setBackgroundColor(getColor(R.color.blueLight))
        button9.text = ""
        button9.isEnabled = true

        player1.clear()
        player2.clear()
        activePlayer = 1

    }
}