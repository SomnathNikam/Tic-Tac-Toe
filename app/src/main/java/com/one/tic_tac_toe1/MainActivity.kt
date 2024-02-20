package com.one.tic_tac_toe1

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val buttons = Array(3) { arrayOfNulls<Button>(3) }
    private var currentPlayer = 1
    private var moves = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeButtons()
    }

    private fun initializeButtons() {
        val gridLayout: GridLayout = findViewById(R.id.gridLayout)

        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j] = createButton(i, j)
                gridLayout.addView(buttons[i][j])
            }
        }
    }

private fun createButton(row: Int, col: Int): Button {
    return Button(this).apply {
        layoutParams = GridLayout.LayoutParams().apply {
            width = 0
            height = 0
            columnSpec = GridLayout.spec(col, 1f)
            rowSpec = GridLayout.spec(row, 1f)
            gravity = Gravity.CENTER
        }
        textSize = 64f
        setOnClickListener { onButtonClick(row, col) }
    }
}

    private fun onButtonClick(row: Int, col: Int) {
        val button = buttons[row][col]

        if (button?.text == "") {
            moves++
            if (currentPlayer == 1) {
                button.text = "X"
                button.setTextColor(resources.getColor(R.color.green))
            } else {
                button.text = "O"
                button.setTextColor(resources.getColor(R.color.blue))
            }

            if (checkForWinner()) {
                showWinner()
            } else if (moves == 9) {
                showDraw()
            } else {
                currentPlayer = if (currentPlayer == 1) 2 else 1
            }
        } else {
            Toast.makeText(this, "Invalid move", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkForWinner(): Boolean {

        for (i in 0..2) {
            // Check rows
            if (buttons[i][0]?.text == buttons[i][1]?.text &&
                buttons[i][1]?.text == buttons[i][2]?.text &&
                buttons[i][0]?.text != ""
            ) {
                highlightWinningLine(buttons[i][0], buttons[i][1], buttons[i][2])
                return true
            }

            if (buttons[0][i]?.text == buttons[1][i]?.text &&
                buttons[1][i]?.text == buttons[2][i]?.text &&
                buttons[0][i]?.text != ""
            ) {
                highlightWinningLine(buttons[0][i], buttons[1][i], buttons[2][i])
                return true
            }
        }


        if (buttons[0][0]?.text == buttons[1][1]?.text &&
            buttons[1][1]?.text == buttons[2][2]?.text &&
            buttons[0][0]?.text != ""
        ) {
            highlightWinningLine(buttons[0][0], buttons[1][1], buttons[2][2])
            return true
        }

        if (buttons[0][2]?.text == buttons[1][1]?.text &&
            buttons[1][1]?.text == buttons[2][0]?.text &&
            buttons[0][2]?.text != ""
        ) {
            highlightWinningLine(buttons[0][2], buttons[1][1], buttons[2][0])
            return true
        }

        return false
    }

    private fun showWinner() {
        val winningColor = resources.getColor(R.color.winningColor)
        Toast.makeText(this, "Player $currentPlayer wins!", Toast.LENGTH_SHORT).show()


        for (i in 0..2) {
            buttons[i][0]?.setTextColor(winningColor)
            buttons[i][1]?.setTextColor(winningColor)
            buttons[i][2]?.setTextColor(winningColor)
        }

        resetGame()
    }

    private fun showDraw() {
        Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show()
        resetGame()
    }

    private fun resetGame() {
        moves = 0
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j]?.text = ""
            }
        }
        currentPlayer = 1
    }
    private fun highlightWinningLine(vararg winningButtons: Button?) {
        val winningColor = resources.getColor(R.color.winningColor)
        for (button in winningButtons) {
            button?.setTextColor(winningColor)
        }
    }
}
