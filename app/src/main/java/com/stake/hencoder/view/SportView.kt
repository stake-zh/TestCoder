package com.stake.hencoder.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.stake.hencoder.R
import com.stake.hencoder.dp

private val CIRCLE_COLOR = Color.parseColor("#90A4AE")
private val HIGHLIGHT_COLOR = Color.parseColor("#FF4081")
private val RING_WIDTH = 20.dp
private val RADIUS = 150.dp

class SportView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        typeface = ResourcesCompat.getFont(context, R.font.font)

    }
    private val rect = Rect()
    private val fontMetrics = Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = RING_WIDTH
        paint.color = CIRCLE_COLOR
        canvas.drawOval(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS, paint)

        paint.color = HIGHLIGHT_COLOR
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS,
            -90f, 225f, false, paint
        )
        paint.textAlign = Paint.Align.CENTER

        paint.textSize = 100.dp
        paint.style = Paint.Style.FILL
//        paint.getTextBounds("abab", 0, "abab".length, rect)
//        canvas.drawText("abab", (width / 2).toFloat(), (height / 2 - (rect.top - rect.bottom) / 2).toFloat(), paint)
        paint.getFontMetrics(fontMetrics)
        canvas.drawText("abab", (width / 2).toFloat(), (height / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2), paint)


    }
}