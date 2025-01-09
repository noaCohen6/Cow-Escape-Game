package com.example.a25a_10357_hw.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.example.a25a_10357_hw.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a25a_10357_hw.interfaces.Callback_HighScoreItemClicked
import com.example.a25a_10357_hw.models.HighScore
import com.example.a25a_10357_hw.utilities.DataManager
import com.example.a25a_10357_hw.utilities.HighScoreAdapter


class HighScoreFragment : Fragment() {

    private lateinit var high_score_RV_list: RecyclerView
    private lateinit var adapter: HighScoreAdapter
    private val scores = ArrayList<HighScore>()

    var highScoreItemClicked: Callback_HighScoreItemClicked? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_high_score, container, false)
        findViews(v)
        initViews(v)
        return v
    }


    private fun initViews(v: View) {
        high_score_RV_list.layoutManager = LinearLayoutManager(context)

        adapter = HighScoreAdapter(scores) { lat, lon ->
            itemClicked(lat, lon)
        }
        high_score_RV_list.adapter = adapter

        loadHighScores()

    }
    private fun loadHighScores() {
        val scores = DataManager.getInstance().getAllHighScores()
        adapter.updateScores(scores)
    }

    private fun itemClicked(lat: Double, lon: Double) {
        highScoreItemClicked?.highScoreItemClicked(lat,lon)
    }

    private fun findViews(v: View) {
        high_score_RV_list = v.findViewById(R.id.high_score_RV_list)
    }
}