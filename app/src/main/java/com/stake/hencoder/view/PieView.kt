package com.stake.hencoder.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toColorInt
import com.stake.hencoder.dp
import kotlin.math.cos
import kotlin.math.sin

val radius = 150.dp
private val angles = floatArrayOf(60f, 90f, 150f, 60f)
private val colors = intArrayOf("#C2185B".toColorInt(), "#00ACC1".toColorInt(), "#558B2F".toColorInt(), "#5D4037".toColorInt())
private val offset_length = 20.dp
private const val i = 2

class PieView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        var startAngle = 0f;
        for ((index, angle) in angles.withIndex()) {
            if (index == i) {
                canvas.save()
                canvas.translate((offset_length * cos(Math.toRadians((startAngle + angle / 2).toDouble()))).toFloat(),
                        (offset_length * sin(Math.toRadians((startAngle + angle / 2).toDouble()))).toFloat())
            }
            paint.color = colors[index]
            canvas.drawArc(width / 2f - radius, height / 2 - radius, width / 2f + radius,
                    height / 2 + radius, startAngle, angle, true, paint)
            startAngle += angle
            if (index == i) {
                canvas.restore()
            }
        }
    }
}