package com.example.a25a_10357_hw.interfaces

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.example.a25a_10357_hw.logic.GameManager

interface GameObject {
    val imageResource: Int
    fun onCollision(context: Context, gameManager: GameManager, hearts: Array<AppCompatImageView>): Boolean
}