package com.example.a25a_10357_hw.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a25a_10357_hw.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.openlocationcode.OpenLocationCode
import com.example.a25a_10357_hw.models.HighScore
import com.example.a25a_10357_hw.utilities.DataManager
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null
    private var markers: MutableList<HighScore> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        mapView = view.findViewById(R.id.score_MAP)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        return view
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        loadMarkers()
    }

    private fun loadMarkers() {
        markers = DataManager.getInstance().getAllHighScores().toMutableList()
        markers.forEach { score ->
            addMarker(score)
        }

        // Zoom to include all markers if there are any
        if (markers.isNotEmpty()) {
            val firstLocation = LatLng(markers[0].latitude, markers[0].longitude)
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 10f))
        }
    }

    private fun addMarker(score: HighScore) {
        val position = LatLng(score.latitude, score.longitude)
        googleMap?.addMarker(
            MarkerOptions()
                .position(position)
                .title("Score: ${score.points}")
        )
    }

    fun zoomToLocation(latitude: Double, longitude: Double) {
        val location = LatLng(latitude, longitude)
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(location, 15f)
        )
    }

    // Lifecycle methods
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}