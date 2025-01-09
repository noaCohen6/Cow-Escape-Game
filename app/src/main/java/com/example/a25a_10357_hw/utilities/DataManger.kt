package com.example.a25a_10357_hw.utilities

import android.util.Log
import com.example.a25a_10357_hw.models.HighScore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class DataManager private constructor() {
    private val gson = Gson()
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private var highScores: ArrayList<HighScore> = ArrayList()

    companion object {
        private const val HIGH_SCORES_KEY = "HIGH_SCORES_KEY"

        @Volatile
        private var instance: DataManager? = null

        fun getInstance(): DataManager {
            return instance ?: synchronized(this) {
                instance ?: DataManager().also { instance = it }
            }
        }
    }

    fun init(sharedPreferencesManager: SharedPreferencesManager) {
        this.sharedPreferencesManager = sharedPreferencesManager
        loadHighScores()
    }

    private fun loadHighScores() {
        val highScoresJson = sharedPreferencesManager.getString(HIGH_SCORES_KEY, "[]")
        val type = object : TypeToken<ArrayList<HighScore>>() {}.type
        highScores = gson.fromJson(highScoresJson, type)
    }

    fun saveHighScore(highScore: HighScore) {
        if (highScore.latitude == 0.0 && highScore.longitude == 0.0) {
            return
        }

        if (!isValidLocation(highScore.latitude, highScore.longitude)) {
            return
        }

        highScores.add(highScore)

        highScores.sortByDescending { it.points }
        if (highScores.size > 10) {
            highScores = ArrayList(highScores.take(10))
        }
        val highScoresJson = gson.toJson(highScores)
        sharedPreferencesManager.putString(HIGH_SCORES_KEY, highScoresJson)
    }

    private fun isValidLocation(latitude: Double, longitude: Double): Boolean {
        return latitude >= -90 && latitude <= 90 &&
                longitude >= -180 && longitude <= 180
    }

    fun getAllHighScores(): List<HighScore> = highScores

    fun getTopHighScores(limit: Int): List<HighScore> {
        return highScores.sortedByDescending { it.points }.take(limit)
    }
}