package com.example.a25a_10357_hw

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a25a_10357_hw.utilities.DataManager
import com.example.a25a_10357_hw.utilities.SharedPreferencesManager
import com.example.a25a_10357_hw.utilities.SingleSoundPlayer
import com.google.android.material.button.MaterialButton
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textview.MaterialTextView

class EntryScreenActivity : AppCompatActivity() {

    private lateinit var entry_LBL_title: MaterialTextView
    private lateinit var entry_LBL_control: MaterialTextView
    private lateinit var entry_SW_controls: MaterialSwitch
    private lateinit var entry_LBL_difficulty: MaterialTextView
    private lateinit var entry_SW_difficulty: MaterialSwitch
    private lateinit var entry_BTN_start: MaterialButton
    private lateinit var entry_BTN_record: MaterialButton
    private lateinit var entry_LBL_arrows: MaterialTextView
    private lateinit var entry_LBL_sensors: MaterialTextView
    private lateinit var entry_LBL_easy: MaterialTextView
    private lateinit var entry_LBL_hard: MaterialTextView
    private var isSensorMode = false
    private var isHardMode = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_screen)
        SharedPreferencesManager.init(this)
        DataManager.getInstance().init(SharedPreferencesManager.getInstance())
        findViews()
        initViews()

    }

    private fun findViews() {
        entry_LBL_title = findViewById(R.id.entry_LBL_title)
        entry_LBL_control = findViewById(R.id.entry_LBL_control)
        entry_SW_controls = findViewById(R.id.entry_SW_controls)
        entry_LBL_difficulty = findViewById(R.id.entry_LBL_difficulty)
        entry_SW_difficulty = findViewById(R.id.entry_SW_difficulty)
        entry_BTN_start = findViewById(R.id.entry_BTN_start)
        entry_BTN_record = findViewById(R.id.entry_BTN_record)
        entry_LBL_arrows = findViewById(R.id.entry_LBL_arrows)
        entry_LBL_sensors = findViewById(R.id.entry_LBL_sensors)
        entry_LBL_easy = findViewById(R.id.entry_LBL_easy)
        entry_LBL_hard = findViewById(R.id.entry_LBL_hard)

    }

    private fun initViews() {
        // Listener for control type switch
        entry_SW_controls.setOnCheckedChangeListener { _, isChecked ->
            isSensorMode = isChecked
        }

        entry_SW_difficulty.setOnCheckedChangeListener { _, isChecked ->
            isHardMode = isChecked
        }

        // Listener for start game button
        entry_BTN_start.setOnClickListener {
            startGame()

        }
        entry_BTN_record.setOnClickListener {
            val intent = Intent(this, HighScoreActivity::class.java)
            startActivity(intent)
        }
    }
    private fun startGame() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("CONTROL_TYPE", isSensorMode)
            putExtra("DIFFICULTY", isHardMode)
        }
        startActivity(intent)
        finish()
    }

}