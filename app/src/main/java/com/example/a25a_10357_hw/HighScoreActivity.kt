package com.example.a25a_10357_hw

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.a25a_10357_hw.fragments.HighScoreFragment
import com.example.a25a_10357_hw.fragments.MapFragment
import com.example.a25a_10357_hw.interfaces.Callback_HighScoreItemClicked
import com.google.android.material.button.MaterialButton

class HighScoreActivity : AppCompatActivity(), Callback_HighScoreItemClicked {

    private lateinit var main_FRAME_list: FrameLayout
    private lateinit var main_FRAME_map: FrameLayout
    private lateinit var mapFragment: MapFragment
    private lateinit var highScoreFragment: HighScoreFragment
    private lateinit var main_menu_BTN_back: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)

        findViews()
        initViews()
    }

    private fun findViews() {
        main_FRAME_list = findViewById(R.id.main_FRAME_list)
        main_FRAME_map = findViewById(R.id.main_FRAME_map)
        main_menu_BTN_back = findViewById(R.id.main_menu_BTN_back)
    }

    private fun initViews() {
        main_menu_BTN_back.setOnClickListener {
            val intent = Intent(this, EntryScreenActivity::class.java)
            startActivity(intent)
            finish()
        }

        mapFragment = MapFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_FRAME_map, mapFragment)
            .commit()

        highScoreFragment = HighScoreFragment()
        highScoreFragment.highScoreItemClicked = this
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_FRAME_list, highScoreFragment)
            .commit()
    }

    override fun highScoreItemClicked(lat: Double, lon: Double) {
        mapFragment.zoomToLocation(lat, lon)
    }
}