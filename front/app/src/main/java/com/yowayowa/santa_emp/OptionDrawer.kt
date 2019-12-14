package com.yowayowa.santa_emp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.widget.*
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OptionDrawer : AppCompatActivity() , OnMapReadyCallback {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mMap: GoogleMap
    private var mLocationManager: LocationManager? = null
    private lateinit var userLocate: LatLng

    private var childLocates:MutableList<childLocationClass> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        //Maps
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option_drawer)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapw) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Option
       // super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_option_drawer)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        /* val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_valus, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        getAllJSONData()
        //トグルスイッチのハンドラ
       val  switchemp = Switch(this)
        switchemp.setOnCheckedChangeListener({button, isChecked ->
            val text = if (isChecked) "checked" else "unchecked"
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        })
        val menuItem1 = navView.menu.findItem(R.id.app_bar_switch)
        menuItem1.actionView = switchemp

        //ValusButtonのハンドラ
       navView.setNavigationItemSelectedListener {
           when(it.itemId){
               R.id.app_bar_switch ->{
                   Toast.makeText(this,"hoge",Toast.LENGTH_SHORT).show()
               }
               R.id.nav_valus ->{
                   Toast.makeText(this,"時間だ、答えを聞こう。",Toast.LENGTH_SHORT).show()
                   Valus()
               }


           }
           true
       }
    }


    //Maps
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


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.option_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //パーミッション許可を乞うダイアログから与えられた応答に対するリアクション
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            permissionsRequestCode -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    locationStart()
                    Toast.makeText(applicationContext,"gpsの使用許可が下りました。位置情報を取得します。", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext,"gpsの使用許可が下りませんでした。位置情報を取得できません。", Toast.LENGTH_SHORT).show()
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
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == 0){
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

    fun getAllJSONData(){
        var ls:MutableList<childLocationClass> = mutableListOf()
        val service = createService().create(API_Interface.API_getall::class.java)
        service.API()
            .enqueue(object : Callback<List<childLocationClass>?> {
                override fun onResponse(call: Call<List<childLocationClass>?>, response: Response<List<childLocationClass>?>) {
                    if(response.isSuccessful){
                        var ticker = response.body()
                        if(ticker != null) {
                            for (i in 0 until ticker.size) {
                                childLocates.add(childLocationClass(
                                    ticker[i].id,
                                    ticker[i].latitude,
                                    ticker[i].longitude
                                ))
                            }
                        }
                        addChildrenMarker()
                    }else {
                        Toast.makeText(applicationContext,"JSON取得失敗.",Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<List<childLocationClass>?>, t: Throwable) {
                    Toast.makeText(applicationContext,"エラー.",Toast.LENGTH_SHORT).show()
                }
            } )
    }

    fun Valus(){
        val service = createService().create(API_Interface.API_Valus::class.java)
        service.API()
            .enqueue(object : Callback<Boolean?> {
                override fun onResponse(call: Call<Boolean?>, response: Response<Boolean?>) {
                    if(response.isSuccessful){
                        var ticker = response.body()
                        if(ticker != null) {
                            if (ticker){
                                Toast.makeText(applicationContext,"return GO.",Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(applicationContext,"return WAIT.",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else {
                        Toast.makeText(applicationContext,"JSON取得失敗.",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Boolean?>, t: Throwable) {
                    Toast.makeText(applicationContext,"エラー.",Toast.LENGTH_SHORT).show()
                }
            } )
    }


    fun createService():Retrofit {
        val url = "http://133.242.224.119:5000/"
        val retro = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retro
    }

    //childLocatesに格納された位置情報らをピンとして配置する
    fun addChildrenMarker(){
        for (item in childLocates){
            val lati = item.latitude
            val log = item.longitude
            Log.d("Atria",lati.toString())
            Log.d("Atria",log.toString())
            val tmp = LatLng(item.latitude.toDouble(),item.longitude.toDouble())
            mMap.addMarker(MarkerOptions().position(tmp).title(item.id.toString()))
        }
    }
}
