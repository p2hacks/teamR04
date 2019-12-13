package com.yowayowa.santa_emp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in FutureUniv. Hakodate and move the camera
        val sumple = LatLng(41.8418174,140.7669687)
        mMap.addMarker(MarkerOptions().position(sumple).title("はこだて未来大学"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sumple))
        zoomMap(sumple.latitude,sumple.longitude)
    }

    private fun zoomMap(latitude: Double,longitude: Double) { // 表示する東西南北の緯度経度を設定する
        val south = latitude * (1 - 0.00005)
        val west = longitude * (1 - 0.00005)
        val north = latitude * (1 + 0.00005)
        val east = longitude * (1 + 0.00005)
        val bounds = LatLngBounds.builder()
            .include(LatLng(south, west))
            .include(LatLng(north, east))
            .build()
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, 0))
    }
}
