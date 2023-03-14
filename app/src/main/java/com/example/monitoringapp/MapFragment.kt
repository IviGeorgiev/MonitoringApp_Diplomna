package com.example.monitoringapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    companion object {
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        mapView = view.findViewById(R.id.google_map)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.uiSettings.isMyLocationButtonEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled = true
        enableMyLocation()
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
            val fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        googleMap.addMarker(
                            MarkerOptions()
                                .position(currentLatLng)
                                .title("Current location")
                        )
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                    }
                }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }
}