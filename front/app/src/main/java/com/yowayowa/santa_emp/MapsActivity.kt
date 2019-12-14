@file:Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")

package com.yowayowa.santa_emp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONException


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var mLocationManager: LocationManager? = null
    private lateinit var userLocate:LatLng


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        JSONRead()
    }

    private val permissionsRequestCode:Int = 1000;

    //権限周り
    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) { // パーミッションの許可を取得する

            ActivityCompat.requestPermissions(this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                permissionsRequestCode
            )
        }else locationStart()
    }

    //パーミッション許可を乞うダイアログから与えられた応答に対するリアクション
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            permissionsRequestCode -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    locationStart()
                    Toast.makeText(applicationContext,"gpsの使用許可が下りました。位置情報を取得します。",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext,"gpsの使用許可が下りませんでした。位置情報を取得できません。",Toast.LENGTH_SHORT).show()
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    //位置情報をgps->Internetの順で取得し、マップ上に位置を表示させる
    private fun locationStart(){
        mLocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == 0){
            var myLocate = mLocationManager!!.getLastKnownLocation("gps")
            if(myLocate == null){
                myLocate = mLocationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
            if(mMap != null){
                mMap.isMyLocationEnabled = true
                userLocate = LatLng(myLocate.latitude,myLocate.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocate))
                zoomMap(userLocate.latitude,userLocate.longitude)
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near はこだて未来大学 in Japan.(Edited)
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in FutureUniv.Hakodate and move the camera
        val sample = LatLng(41.8418174,140.7669687)
        mMap.addMarker(MarkerOptions().position(sample).title("はこだて未来大学"))

        checkLocationPermission()
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

    //JSON
    //ごりごりのヤクザコード.これはだめな例です.
    private fun JSONRead(){
        val sampleJSON:String ="[{\"id\":1,\"latitude\":41.8418174,\"longitude\":140.7669687},{\"id\":2,\"latitude\":41.7969778,\"longitude\":140.7547704}]"
        var ls = arrayListOf<childLocationClass>()
        try {
            val obj = JSONArray(sampleJSON)
            for (i in 0 until obj.length()){
                var tmp = obj.getJSONObject(i)
                ls.add(childLocationClass(tmp.getString("id").toInt(),tmp.getString("latitude").toFloat(),tmp.getString("longitude").toFloat()))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.d("Atria",e.toString())
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
    }
}
