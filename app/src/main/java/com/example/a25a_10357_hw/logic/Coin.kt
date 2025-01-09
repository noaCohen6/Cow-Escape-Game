package com.example.a25a_10357_hw.logic

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.example.a25a_10357_hw.R
import com.example.a25a_10357_hw.interfaces.GameObject
import com.example.a25a_10357_hw.utilities.Constants
import com.example.a25a_10357_hw.utilities.SingleSoundPlayer

class Coin : GameObject {
    override val imageResource: Int = R.drawable.coin

    override fun onCollision(context: Context, gameManager: GameManager, hearts: Array<AppCompatImageView>): Boolean {
        var ssp: SingleSoundPlayer = SingleSoundPlayer(context)
        ssp.playSound(R.raw.coin_sound)
        gameManager.increaseScore(Constants.GameLogic.SCORE_POINTS)
        return false
    }
}