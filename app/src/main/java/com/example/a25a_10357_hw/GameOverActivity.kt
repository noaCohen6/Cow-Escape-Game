package com.example.a25a_10357_hw

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.a25a_10357_hw.logic.GameManager
import com.example.a25a_10357_hw.utilities.Constants
import com.google.android.material.textview.MaterialTextView

class GameOverActivity : AppCompatActivity(){

    private lateinit var game_over_LBL: MaterialTextView
    private val DISPLAY_TIME = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        findViews()
        initViews()

        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, DISPLAY_TIME)
    }

    private fun initViews() {
        val bundle: Bundle? = intent.extras
        val message = bundle?.getString(Constants.BundleKeys.STATUS_KEY,"Game Over!")
        game_over_LBL.text = message
    }

    private fun findViews() {
        game_over_LBL = findViewById(R.id.game_over_LBL)
    }

}