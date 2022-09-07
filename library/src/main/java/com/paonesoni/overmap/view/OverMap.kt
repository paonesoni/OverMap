package com.paonesoni.overmap.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.paonesoni.overmap.R
import com.paonesoni.overmap.datapath.PathData
import com.paonesoni.overmap.utils.getPoint
import com.google.android.gms.maps.GoogleMap
import java.lang.Math.toRadians
import java.util.ArrayList


class OverMap : View {
    private var myArc = Path()
    private var archPaint: Paint? = null
    private var archShadowPaint: Paint? = null
    private var archCapStartPaint: Paint? = null
    private var archCapEndPaint: Paint? = null
    private var pointStartOld: Point? = null
    private var pointEndOld: Point? = null
    private var mMap: GoogleMap? = null
    private var points = ArrayList<PathData>()
    private var point: PathData? = null
    private var pathColor:Int = Color.BLACK
    private var pathShadowColor:Int = Color.GRAY
    private var capStartColor:Int = Color.TRANSPARENT
    private var capEndColor:Int = Color.TRANSPARENT
    private var pathWidth:Float = 7f
    private var pathShadowWidth:Float = 8f
    private var capSize: Float = 15f

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        context?.apply {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.OverMap)
             pathColor = attributes.getColor(R.styleable.OverMap_pathColor, Color.BLACK)
             pathShadowColor = attributes.getColor(R.styleable.OverMap_pathShadowColor, Color.GRAY)
             capStartColor = attributes.getColor(R.styleable.OverMap_capStartColor, Color.TRANSPARENT)
             capEndColor = attributes.getColor(R.styleable.OverMap_capEndColor, Color.TRANSPARENT)
             pathWidth = attributes.getDimension(R.styleable.OverMap_pathWidth, 7f)
             pathShadowWidth = attributes.getDimension(R.styleable.OverMap_pathShadowWidth, 8f)
             capSize = attributes.getDimension(R.styleable.OverMap_capSize, 15f)
            attributes.recycle()
            init(pathColor, pathShadowColor,capStartColor, capEndColor, pathWidth, pathShadowWidth,capSize)
        }
    }

    constructor(context: Context?) : super(context, null) {
    }

    private fun init(pathColor: Int, pathShadowColor: Int, capStartColor: Int, capEndColor: Int,
        pathWidth: Float, pathShadowWidth: Float, capSize: Float) {
        archPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        archPaint!!.style = Paint.Style.STROKE
        archPaint!!.color = pathColor
        archPaint!!.strokeWidth = pathWidth
        archPaint!!.isAntiAlias = true
        archPaint!!.isDither = true
        archPaint!!.strokeJoin = Paint.Join.ROUND
        archPaint!!.strokeCap = Paint.Cap.ROUND

        archShadowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        archShadowPaint!!.style = Paint.Style.STROKE
        archShadowPaint!!.maskFilter = BlurMaskFilter(pathShadowWidth, BlurMaskFilter.Blur.NORMAL)
        archShadowPaint!!.color = pathShadowColor
        archShadowPaint!!.strokeWidth = pathShadowWidth

        archCapStartPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        archCapStartPaint!!.style = Paint.Style.FILL
        archCapStartPaint!!.color = capStartColor
        archCapStartPaint!!.strokeWidth = capSize

        archCapEndPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        archCapEndPaint!!.style = Paint.Style.FILL
        archCapEndPaint!!.color = capEndColor
        archCapEndPaint!!.strokeWidth = capSize

    }

    /**
     * Draw single Arch path
     */
    fun arch(point: PathData, googleMap: GoogleMap?): OverMap {
        googleMap?.apply {
            mMap = this
            this@OverMap.point = point
        }
        invalidate()
        return this
    }

    /**
     * Draw multiple path
     */
    fun arch(pointList: ArrayList<PathData>, googleMap: GoogleMap?): OverMap {
        googleMap?.apply {
            mMap = this
            points = pointList
        }
        invalidate()
        return this
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var _cupSize = 15f
        if (points.isNotEmpty()){
            for (p in points){
                p.style?.apply {
                    _cupSize = capSize
                    init(pathColor, pathShadowColor,capStartColor, capEndColor, pathWidth, pathShadowWidth,capSize)
                }?: kotlin.run {
                    _cupSize = capSize
                    init(pathColor, pathShadowColor, capStartColor, capEndColor, pathWidth, pathShadowWidth, capSize)
                }
                update(canvas, getPoint(p.start, mMap!!), getPoint(p.end, mMap!!), _cupSize)
            }
        }else{
            point?.apply {
                style?.apply {
                    _cupSize = capSize
                    init(pathColor, pathShadowColor,capStartColor, capEndColor, pathWidth, pathShadowWidth,capSize)
                }
                update(canvas, getPoint(start, mMap!!), getPoint(end, mMap!!),_cupSize)
            }
        }
    }

    /**
     * Add and update path
     */
    private fun update(canvas: Canvas, pointStart: Point, pointEnd: Point, capSize: Float) {
        canvas.apply {
            drawLine(
                pointStart.x.toFloat(), pointStart.y.toFloat(), pointEnd.x.toFloat(),
                pointEnd.y.toFloat(), archShadowPaint!!
            )
            drawCircle(pointStart.x.toFloat(), pointStart.y.toFloat(),
                capSize, archCapStartPaint!!)
            drawCircle(pointEnd.x.toFloat(), pointEnd.y.toFloat(),
                capSize, archCapEndPaint!!)

            if (pointStartOld != null) {
                drawMyPath(canvas, pointStartOld!!, pointEndOld!!, true)
            }
            drawMyPath(canvas, pointStart, pointEnd, false)
            pointStartOld = pointStart
            pointEndOld = pointEnd
        }
    }

    private fun drawMyPath(canvas: Canvas, pointStart: Point, pointEnd: Point, clear: Boolean) {
        val point1x: Int = pointStart.x
        val point1y: Int = pointStart.y
        val point2x: Int = pointEnd.x
        val point2y: Int = pointEnd.y

        val i = 2
        val pointerDifference1: Int = pointStart.x - pointEnd.x
        val pointerDifference2: Int = pointStart.y - pointEnd.y
        var sqrt = kotlin.math.sqrt(
            ((pointerDifference1 * pointerDifference1).toFloat() +
                    (pointerDifference2 * pointerDifference2).toFloat()).toDouble()
        ).toFloat().toInt()


        if (point2x <= point1x) {
            sqrt = -sqrt
        }
        val i2: Int = sqrt / i

        val f1x = point1x.toFloat()
        val f1y = point1y.toFloat()
        val f2x = (point2x - point1x) / 2.0f + f1x
        val f2y = (point2y - point1y) / 2.0f + f1y

        val radians = toRadians(
            kotlin.math.atan2(
                (f2y - f1y).toDouble(), (f2x - f1x).toDouble()
            ).toFloat().toDouble() * 57.29577951308232 - 90.toDouble()
        )

        val d2 = i2.toDouble()
        val cos = kotlin.math.cos(radians) * d2
        val sin = kotlin.math.sin(radians) * d2

        myArc.moveTo(f1x, f1y)
        myArc.cubicTo(
            f1x, f1y, (cos + f2x.toDouble()).toFloat(), (sin + f2y.toDouble()).toFloat(),
            point2x.toFloat(), point2y.toFloat()
        )
        if (clear) {
            myArc.reset()
        } else {
            canvas.drawPath(myArc, archPaint!!)
        }

    }
}