package com.example.a25a_10357_hw

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a25a_10357_hw.logic.GameManager
import com.example.a25a_10357_hw.utilities.Constants
import com.example.a25a_10357_hw.utilities.SignalManager
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    var COW_ROW = 4
    private lateinit var main_BTN_left: MaterialButton
    private lateinit var main_BTN_right: MaterialButton
    private lateinit var main_IMG_hearts: Array<AppCompatImageView>

    private lateinit var gameManager: GameManager
    private var CowcurrentColumn = 1

    private lateinit var cowgirlViews : Array<Array<AppCompatImageView>>
    private lateinit var cow : AppCompatImageView


    private val cowgirlRows = mutableListOf<Int>()
    private val cowgirlColumns = mutableListOf<Int>()
    private var isCollisionHandling = false

    private var gameHandler: Handler? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        gameManager = GameManager(main_IMG_hearts.size)
        initViews()

        initCow()

        updateCowPosition()

        startCowgirlMovement()

    }

    private fun findViews() {
        main_IMG_hearts = arrayOf(
            findViewById(R.id.main_IMG_heart3),
            findViewById(R.id.main_IMG_heart2),
            findViewById(R.id.main_IMG_heart1)
        )
        main_BTN_left = findViewById(R.id.main_BTN_left)
        main_BTN_right = findViewById(R.id.main_BTN_right)
        cowgirlViews  = arrayOf(
            arrayOf(findViewById<AppCompatImageView>(R.id.cowgirl_0_0), findViewById<AppCompatImageView>(R.id.cowgirl_0_1), findViewById<AppCompatImageView>(R.id.cowgirl_0_2)),
            arrayOf(findViewById<AppCompatImageView>(R.id.cowgirl_1_0), findViewById<AppCompatImageView>(R.id.cowgirl_1_1), findViewById<AppCompatImageView>(R.id.cowgirl_1_2)),
            arrayOf(findViewById<AppCompatImageView>(R.id.cowgirl_2_0), findViewById<AppCompatImageView>(R.id.cowgirl_2_1), findViewById<AppCompatImageView>(R.id.cowgirl_2_2)),
            arrayOf(findViewById<AppCompatImageView>(R.id.cowgirl_3_0), findViewById<AppCompatImageView>(R.id.cowgirl_3_1), findViewById<AppCompatImageView>(R.id.cowgirl_3_2)),
            arrayOf(findViewById<AppCompatImageView>(R.id.cowgirl_4_0), findViewById<AppCompatImageView>(R.id.cowgirl_4_1), findViewById<AppCompatImageView>(R.id.cowgirl_4_2)),
            arrayOf(findViewById<AppCompatImageView>(R.id.cowgirl_5_0), findViewById<AppCompatImageView>(R.id.cowgirl_5_1), findViewById<AppCompatImageView>(R.id.cowgirl_5_2))
        )

    }

    private fun initViews() {
        main_BTN_left.setOnClickListener { view: View -> moveCowLeft() }
        main_BTN_right.setOnClickListener {view: View -> moveCowRight() }
        for (row in cowgirlViews) {
            for (view in row) {
                view.visibility = View.INVISIBLE
            }
        }

    }


    private fun initCow() {

        cow = cowgirlViews[COW_ROW][1].apply {
            setImageResource(R.drawable.cow)
            visibility = View.VISIBLE
        }
        CowcurrentColumn = 1
    }

    private fun moveCowgirl() {
        if (isCollisionHandling) return

        // Process existing cowgirls first
        val newRows = mutableListOf<Int>()
        val newColumns = mutableListOf<Int>()
        var collisionOccurred = false

        // Move existing cowgirls
        for (i in cowgirlRows.indices) {
            try {
                // Only hide the cowgirl if it's not at the cow's position
                if (!(cowgirlRows[i] == COW_ROW && cowgirlColumns[i] == CowcurrentColumn)) {
                    cowgirlViews[cowgirlRows[i]][cowgirlColumns[i]].visibility = View.INVISIBLE
                }
            } catch (e: Exception) {
                continue
            }

            val newRow = cowgirlRows[i] + 1

            if (newRow == COW_ROW && cowgirlColumns[i] == CowcurrentColumn) {
                collisionOccurred = true
            } else if (newRow < 5) {
                newRows.add(newRow)
                newColumns.add(cowgirlColumns[i])

                try {
                    // Only show the cowgirl if it's not at the cow's position
                    if (!(newRow == COW_ROW && cowgirlColumns[i] == CowcurrentColumn)) {
                        cowgirlViews[newRow][cowgirlColumns[i]].apply {
                            setImageResource(R.drawable.cowgirl)
                            visibility = View.VISIBLE
                        }
                    }
                } catch (e: Exception) {
                    newRows.removeAt(newRows.lastIndex)
                    newColumns.removeAt(newColumns.lastIndex)
                }
            }
        }

        // Update the main lists
        cowgirlRows.clear()
        cowgirlColumns.clear()
        cowgirlRows.addAll(newRows)
        cowgirlColumns.addAll(newColumns)

        // Add new cowgirls if needed
        if (cowgirlRows.isEmpty() ||
            (cowgirlRows.size < 2 && (0..100).random() < 40)) {
            addNewCowgirl()
        }

        // Ensure cow is always visible
        ensureCowVisibility()

        // Handle collision after all updates
        if (collisionOccurred) {
            isCollisionHandling = true
            handleCollision()
            isCollisionHandling = false
        }
    }

    private fun addNewCowgirl() {
        // Choose random column
        val newColumn = (0..2).random()

        // Add new cowgirl in first row
        cowgirlViews[0][newColumn].apply {
            setImageResource(R.drawable.cowgirl)
            visibility = View.VISIBLE
        }

        // Add position to tracking lists
        cowgirlRows.add(0)
        cowgirlColumns.add(newColumn)
    }


    private fun moveCowLeft() {
        if (CowcurrentColumn > 0) {
            CowcurrentColumn--
            updateCowPosition()
            checkCollisionAtCurrentPosition() // Add collision check
        }
    }

    private fun moveCowRight() {
        if (CowcurrentColumn < 2) {
            CowcurrentColumn++
            updateCowPosition()
            checkCollisionAtCurrentPosition() // Add collision check
        }
    }

    private fun checkCollisionAtCurrentPosition() {
        // Check if there's a cowgirl at the cow's current position
        for (i in cowgirlRows.indices) {
            if (cowgirlRows[i] == COW_ROW && cowgirlColumns[i] == CowcurrentColumn) {
                handleCollision()
                break
            }
        }
    }
    private fun updateCowPosition() {
        val cowViews = cowgirlViews[COW_ROW]
        cowViews.forEach { it.visibility = View.INVISIBLE }

        cowViews[CowcurrentColumn].visibility = View.VISIBLE
        setCowImage(cowViews[CowcurrentColumn])
    }

    private fun ensureCowVisibility() {
        // Ensure cow image is visible at current position
        cowgirlViews[COW_ROW][CowcurrentColumn].apply {
            setImageResource(R.drawable.cow)
            visibility = View.VISIBLE
        }
    }

    private fun setCowImage(imageView: AppCompatImageView) {
        imageView.setImageResource(R.drawable.cow)
    }
    private fun handleCollision() {

        val isGameOver = gameManager.handleCollision(main_IMG_hearts)

        updateCowPosition()

        if (isGameOver) {
            gameHandler?.removeCallbacksAndMessages(null)
            gameHandler = null
            cowgirlRows.clear()
            cowgirlColumns.clear()
            changeActivity("Game Over!")
        }
    }

    private fun changeActivity(message: String) {
        val intent = Intent(this, GameOverActivity::class.java)
        var bundle = Bundle()
        bundle.putString(Constants.BundleKeys.STATUS_KEY, message)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

    private fun startCowgirlMovement() {
        gameHandler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                moveCowgirl()
                gameHandler?.postDelayed(this, 500)
            }
        }
        gameHandler?.post(runnable)
    }

}