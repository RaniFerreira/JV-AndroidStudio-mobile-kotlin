package com.example.myjv

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    companion object {
        private const val EMPTY = ' '

        private val WIN_PATTERNS = arrayOf(
            intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8), // linhas
            intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8), // colunas
            intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)                      // diagonais
        )

        private const val KEY_BOARD = "board"
        private const val KEY_CURRENT_PLAYER = "current_player"
        private const val KEY_GAME_ACTIVE = "game_active"
        private const val KEY_SCORE_X = "score_x"
        private const val KEY_SCORE_O = "score_o"
        private const val KEY_SCORE_DRAW = "score_draw"
        private const val KEY_WIN_PATTERN = "win_pattern"
    }

    private lateinit var buttons: Array<Button>
    private lateinit var tvStatus: TextView
    private lateinit var tvScore: TextView

    private val board = CharArray(9) { EMPTY }
    private var currentPlayer = 'X'
    private var gameActive = true

    private var scoreX = 0
    private var scoreO = 0
    private var scoreDraw = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tvStatus)
        tvScore = findViewById(R.id.tvScore)
        buttons = arrayOf(
            findViewById(R.id.btn0), findViewById(R.id.btn1), findViewById(R.id.btn2),
            findViewById(R.id.btn3), findViewById(R.id.btn4), findViewById(R.id.btn5),
            findViewById(R.id.btn6), findViewById(R.id.btn7), findViewById(R.id.btn8)
        )

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener { onCellClicked(index) }
        }
        findViewById<Button>(R.id.btnRestart).setOnClickListener { resetBoard() }

        if (savedInstanceState != null) {
            restoreState(savedInstanceState)
        } else {
            updateStatusText()
            updateScoreText()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_BOARD, String(board))
        outState.putChar(KEY_CURRENT_PLAYER, currentPlayer)
        outState.putBoolean(KEY_GAME_ACTIVE, gameActive)
        outState.putInt(KEY_SCORE_X, scoreX)
        outState.putInt(KEY_SCORE_O, scoreO)
        outState.putInt(KEY_SCORE_DRAW, scoreDraw)
        if (!gameActive) {
            findWinningPattern()?.let { outState.putIntArray(KEY_WIN_PATTERN, it) }
        }
    }

    private fun restoreState(savedInstanceState: Bundle) {
        savedInstanceState.getString(KEY_BOARD)?.toCharArray()?.copyInto(board)
        currentPlayer = savedInstanceState.getChar(KEY_CURRENT_PLAYER, 'X')
        gameActive = savedInstanceState.getBoolean(KEY_GAME_ACTIVE, true)
        scoreX = savedInstanceState.getInt(KEY_SCORE_X)
        scoreO = savedInstanceState.getInt(KEY_SCORE_O)
        scoreDraw = savedInstanceState.getInt(KEY_SCORE_DRAW)

        buttons.forEachIndexed { index, button ->
            val mark = board[index]
            if (mark != EMPTY) {
                button.text = mark.toString()
                button.setTextColor(colorForPlayer(mark))
                button.isEnabled = false
            }
        }

        val winPattern = savedInstanceState.getIntArray(KEY_WIN_PATTERN)
        if (!gameActive) {
            disableBoard()
            winPattern?.forEach { idx -> highlightCell(idx) }
        }

        updateStatusText(winPattern)
        updateScoreText()
    }

    private fun onCellClicked(index: Int) {
        if (!gameActive || board[index] != EMPTY) return

        board[index] = currentPlayer
        val button = buttons[index]
        button.text = currentPlayer.toString()
        button.setTextColor(colorForPlayer(currentPlayer))
        button.isEnabled = false

        val winPattern = findWinningPattern()
        when {
            winPattern != null -> handleWin(winPattern)
            board.none { it == EMPTY } -> handleDraw()
            else -> {
                currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
                updateStatusText()
            }
        }
    }

    private fun findWinningPattern(): IntArray? =
        WIN_PATTERNS.firstOrNull { (a, b, c) ->
            board[a] != EMPTY && board[a] == board[b] && board[b] == board[c]
        }

    private fun handleWin(pattern: IntArray) {
        gameActive = false
        pattern.forEach { idx -> highlightCell(idx) }
        disableBoard()

        if (currentPlayer == 'X') scoreX++ else scoreO++
        updateStatusText(pattern)
        updateScoreText()
    }

    private fun handleDraw() {
        gameActive = false
        disableBoard()
        scoreDraw++
        updateStatusText(null)
        updateScoreText()
    }

    private fun highlightCell(index: Int) {
        val button = buttons[index]
        button.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(this, R.color.winHighlight)
        )
        button.startAnimation(AnimationUtils.loadAnimation(this, R.anim.win_pulse))
    }

    private fun disableBoard() {
        buttons.forEach { it.isEnabled = false }
    }

    private fun colorForPlayer(player: Char) = ContextCompat.getColor(
        this,
        if (player == 'X') R.color.playerXColor else R.color.playerOColor
    )

    private fun updateStatusText(winPattern: IntArray? = null) {
        tvStatus.text = when {
            !gameActive && winPattern != null -> getString(R.string.status_win, currentPlayer)
            !gameActive -> getString(R.string.status_draw)
            else -> getString(R.string.status_turn, currentPlayer)
        }
    }

    private fun updateScoreText() {
        tvScore.text = getString(R.string.score_format, scoreX, scoreO, scoreDraw)
    }

    private fun resetBoard() {
        board.fill(EMPTY)
        currentPlayer = 'X'
        gameActive = true

        buttons.forEach { button ->
            button.text = ""
            button.isEnabled = true
            button.clearAnimation()
            button.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this, R.color.cellDefault)
            )
        }

        updateStatusText()
    }
}
