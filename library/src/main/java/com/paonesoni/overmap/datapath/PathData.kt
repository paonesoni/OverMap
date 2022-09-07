package com.paonesoni.overmap.datapath

import com.google.android.gms.maps.model.LatLng

data class PathData(val start:LatLng, val end:LatLng, var style: PathStyle? = null)
