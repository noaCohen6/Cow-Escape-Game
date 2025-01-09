package com.example.a25a_10357_hw.utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a25a_10357_hw.R
import com.example.a25a_10357_hw.models.HighScore
import com.google.android.material.textview.MaterialTextView
import java.util.Locale
import java.text.SimpleDateFormat
import java.util.Date

class HighScoreAdapter(
    private val scores: ArrayList<HighScore>,
    private val itemClickListener: (Double, Double) -> Unit
) : RecyclerView.Adapter<HighScoreAdapter.HighScoreViewHolder>() {

    class HighScoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item_LBL_score: MaterialTextView = view.findViewById(R.id.item_LBL_score)
        val item_LBL_date: MaterialTextView = view.findViewById(R.id.item_LBL_date)
        val item_LBL_location: MaterialTextView = view.findViewById(R.id.item_LBL_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_high_score, parent, false)
        return HighScoreViewHolder(view)
    }

    override fun getItemCount() = scores.size

    override fun onBindViewHolder(holder: HighScoreViewHolder, position: Int) {
        val score = scores[position]

        holder.item_LBL_score.text = "Score: ${score.points}"
        holder.item_LBL_date.text = "Date: ${formatDate(score.date)}"
        holder.item_LBL_location.text = "Location: ${formatLocation(score.latitude, score.longitude)}"

        holder.item_LBL_location.setOnClickListener {
            itemClickListener.invoke(score.latitude, score.longitude)
        }
    }

    private fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    private fun formatLocation(lat: Double, lon: Double): String {
        return String.format("%.6f, %.6f", lat, lon)
    }
    fun updateScores(newScores: List<HighScore>) {
        scores.clear()
        scores.addAll(newScores)
        notifyDataSetChanged()
    }
}