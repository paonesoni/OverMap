package com.paonesoni.overmap.utils

import android.graphics.Point
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.LatLng

 fun getPoint(latLng: LatLng, googleMap: GoogleMap): Point {
    val projection: Projection = googleMap.projection
    return projection.toScreenLocation(latLng)
}