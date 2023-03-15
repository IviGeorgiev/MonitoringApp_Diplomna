package com.example.monitoringapp.views

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
import com.example.monitoringapp.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
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
        addMarkers()
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

    private fun addMarkers() {
        val locations = listOf(
            LatLng(42.664123, 23.369225),
            LatLng(42.678846, 23.337501),
            LatLng(42.669762, 23.310688),
            LatLng(42.661173, 23.311035),
            LatLng(42.659339, 23.306804),
            LatLng(42.688609,23.319752),
            LatLng(42.455982, 27.413680),
            LatLng(42.657618, 23.311264),
            LatLng(42.639590, 23.360411),
            LatLng(42.689417, 23.420543),
            LatLng(42.682852, 23.317758),
            LatLng(42.702426, 23.256102),
            LatLng(42.707131, 23.271009),
            LatLng(42.447598, 27.463381),
            LatLng(39.116450,125.805773),
            LatLng(37.2040,-115.809828),
            LatLng(42.662283, 23.373834),
            LatLng(42.652840, 23.353219),
            LatLng(42.653393, 23.354331)
        )

        for (location in locations) {
            googleMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .title("Observation")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            )
        }
    }
}