package com.example.a25a_10357_hw.logic

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.example.a25a_10357_hw.utilities.SignalManager

class GameManager(private val lifeCount: Int = 3) {
     var lives: Int = lifeCount
        private set

    private var isGameOver = false

    fun handleCollision(main_IMG_hearts: Array<AppCompatImageView>): Boolean {
        if (isGameOver) return true

        SignalManager.getInstance().vibrate()

        reduceLife(main_IMG_hearts)
        if (lives > 0) {
            SignalManager.getInstance().toast("Collision! $lives lives remaining")
        }

        if (isGameOver()) {
            isGameOver = true
            return true
        }

        return false
    }

    private fun reduceLife(main_IMG_hearts: Array<AppCompatImageView>) {
        if (lives > 0) {
            lives--
            main_IMG_hearts[lives].visibility = View.INVISIBLE
        }
    }
    private fun isGameOver(): Boolean {
        return lives <= 0
    }

}