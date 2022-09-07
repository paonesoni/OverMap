package com.paonesoni.overmap.datapath

import android.graphics.Color

data class PathStyle(val pathColor: Int = Color.BLACK, val pathShadowColor:Int = Color.GRAY,
                     val capStartColor:Int = Color.TRANSPARENT, val capEndColor:Int = Color.TRANSPARENT,
                     val pathWidth:Float = 7f, val pathShadowWidth:Float = 8f, val capSize:Float = 15f)
