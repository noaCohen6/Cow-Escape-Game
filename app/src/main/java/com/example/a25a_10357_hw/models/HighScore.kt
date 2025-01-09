package com.example.a25a_10357_hw.models

data class HighScore(
    val points: Int,
    val date: Long,
    val latitude: Double,
    val longitude: Double
) {
    class Builder {
        private var points: Int = 0
        private var date: Long = System.currentTimeMillis()
        private var latitude: Double = 0.0
        private var longitude: Double = 0.0

        fun points(points: Int) = apply { this.points = points }
        fun date(date: Long) = apply { this.date = date }
        fun latitude(latitude: Double) = apply { this.latitude = latitude }
        fun longitude(longitude: Double) = apply { this.longitude = longitude }

        fun build() = HighScore(points, date, latitude, longitude)
    }
}