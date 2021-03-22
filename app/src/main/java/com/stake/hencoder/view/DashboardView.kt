package com.stake.hencoder.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathDashPathEffect
import android.graphics.PathEffect
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import com.stake.hencoder.dp
import kotlin.math.cos
import kotlin.math.sin

private val RADIUS = 150f.dp
private val LENGTH = 120f.dp
const val OPEN_ANGLE = 120
val dash_width = 2.dp
private val DASH_LENGTH = 10f.dp

class DashboardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 3f.dp
    }
    private val path = Path()
    private val dashPath = Path().apply {
        addRect(0f, 0f, dash_width, DASH_LENGTH, Path.Direction.CCW)
    }
    private lateinit var pathEffect: PathEffect

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        path.addArc(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS,
            (90 + OPEN_ANGLE / 2).toFloat(), (360 - OPEN_ANGLE).toFloat())
        val pathMeasure = PathMeasure(path, false)
        pathEffect = PathDashPathEffect(dashPath, (pathMeasure.length - dash_width) / 20f, 0f, PathDashPathEffect.Style.ROTATE)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)

        paint.pathEffect = pathEffect
        canvas.drawPath(path, paint)
        paint.pathEffect = null


        canvas.drawLine(width / 2f, height / 2f, (width / 2f + LENGTH * cos(Math.toRadians((90 + OPEN_ANGLE / 2 +
            (360 - OPEN_ANGLE) / 20 * 5f).toDouble()))).toFloat(), (height / 2f + LENGTH * sin(Math.toRadians((90 +
            OPEN_ANGLE / 2 + (360 - OPEN_ANGLE) / 20 * 5f).toDouble()))).toFloat(), paint)
    }

}