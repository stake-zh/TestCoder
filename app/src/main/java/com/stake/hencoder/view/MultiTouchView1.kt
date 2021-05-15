package com.stake.hencoder.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.stake.hencoder.dp
import com.stake.hencoder.getAvatar

class MultiTouchView1 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources, 200.dp.toInt())
    private var offsetX = 0f
    private var offsetY = 0f
    private var downX = 0f
    private var downY = 0f
    private var trackingPointerId = 0

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                trackingPointerId = event.getPointerId(0)
                downX = event.x
                downY = event.y
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                trackingPointerId = event.getPointerId(event.actionIndex)
                downX = event.getX(event.actionIndex)
                downY = event.getY(event.actionIndex)
            }
            MotionEvent.ACTION_MOVE -> {
                val trackingPointer = event.findPointerIndex(trackingPointerId)
                offsetX += event.getX(trackingPointer) - downX
                offsetY += event.getY(trackingPointer) - downY
                downX = event.getX(trackingPointer)
                downY = event.getY(trackingPointer)
                invalidate()
            }
            MotionEvent.ACTION_POINTER_UP -> {
                val pointerId = event.getPointerId(event.actionIndex)
                if (pointerId == trackingPointerId) {
                    val index =
                        if (event.actionIndex == event.pointerCount - 1) {
                            event.pointerCount - 2
                        } else {
                            event.pointerCount - 1
                        }
                    trackingPointerId = event.getPointerId(index)
                    downX = event.getX(index)
                    downY = event.getY(index)
                }
            }
        }
        return true
    }
}