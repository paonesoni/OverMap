package com.paonesoni.overlaymap

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.paonesoni.overmap.datapath.PathData
import com.paonesoni.overmap.datapath.PathStyle
import com.paonesoni.overmap.view.OverMap
import com.google.android.gms.maps.model.LatLng
import com.paonesoni.overlaymap.databinding.ActivityMapsBinding
import com.google.android.gms.maps.*
import java.util.ArrayList

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var customizeMap :OverMap
    private var points = ArrayList<PathData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        customizeMap  = binding.customizeMap

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val styleOne = PathStyle(pathColor = Color.YELLOW, pathShadowColor = Color.GRAY,
            capStartColor = Color.RED, capEndColor = Color.RED, pathWidth = 10f,
            pathShadowWidth = 10f, capSize = 20f)

        val styleTwo = PathStyle(pathColor = ContextCompat.getColor(this,R.color.blue),
            pathShadowColor = ContextCompat.getColor(this,R.color.blue_light),
            capStartColor = ContextCompat.getColor(this,R.color.black),
            capEndColor = ContextCompat.getColor(this,R.color.black),
            pathWidth = 5f, pathShadowWidth = 15f, capSize = 15f)


        points.add(PathData(LatLng(19.007606, 72.817380), LatLng(18.994460, 72.845875)))
        points.add(PathData(LatLng(19.041524, 72.865273), LatLng(19.035682, 72.930333), styleOne ))
        points.add(PathData(LatLng(19.085006, 72.848965), LatLng(19.058399, 72.874199), styleTwo))

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val latLong = LatLng(19.048501, 72.857892)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 13f))

        mMap.setOnCameraMoveListener {
//            customizeMap.arch(PathData(LatLng(19.007606, 72.817380), LatLng(19.069756, 72.875916)), mMap)
            customizeMap.arch(points, googleMap)
        }

    }
}