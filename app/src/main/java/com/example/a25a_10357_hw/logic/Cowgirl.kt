package com.example.a25a_10357_hw.logic

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.example.a25a_10357_hw.R
import com.example.a25a_10357_hw.interfaces.GameObject
import com.example.a25a_10357_hw.utilities.SingleSoundPlayer

class Cowgirl : GameObject {
    override val imageResource: Int = R.drawable.cowgirl

    override fun onCollision(context: Context, gameManager: GameManager, hearts: Array<AppCompatImageView>): Boolean {
        var ssp: SingleSoundPlayer = SingleSoundPlayer(context)
        ssp.playSound(R.raw.yeehaw)
        return gameManager.handleCollision(hearts)
    }
}