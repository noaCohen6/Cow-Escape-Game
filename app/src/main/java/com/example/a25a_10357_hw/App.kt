package com.example.a25a_10357_hw

import android.app.Application
import com.example.a25a_10357_hw.utilities.DataManager
import com.example.a25a_10357_hw.utilities.SharedPreferencesManager
import com.example.a25a_10357_hw.utilities.SignalManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SignalManager.init(this)
        SharedPreferencesManager.init(this)

    }

}