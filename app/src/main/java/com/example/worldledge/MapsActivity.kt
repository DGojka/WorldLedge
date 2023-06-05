package com.example.worldledge

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.worldledge.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.data.geojson.GeoJsonLayer
import java.util.Random


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.argb(50,0,0,0)
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
        try {
            // Apply custom map style
            mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.defaultmapstyle
                )
            )
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }


        // Add a marker in Sydney and move the camera
        val sydney = LatLng(54.0919, 18.777)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker Tczew"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        val layer = GeoJsonLayer(mMap, R.raw.world_1800, applicationContext)
        layer.defaultPolygonStyle.apply {
            this.strokeColor = Color.BLACK
            strokeWidth = 3f
        }
      /*  layer.features.forEach {
            it.polygonStyle.apply {
                fillColor = Color.RED
            }
        }*/
        layer.addLayerToMap()
    }

    private fun generateRandomColor(): Int {
        val r = Random()
        val red = r.nextInt(256)
        val green = r.nextInt(256)
        val blue = r.nextInt(256)

        return Color.rgb(red, green, blue)
    }
}