package com.stake.hencoder.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import com.stake.hencoder.dp
import com.stake.hencoder.getAvatar

private val IMAGE_SIZE = 300.dp.toInt()
private const val EXTRA_SCALE_FACTOR = 1.5f

class ScalableImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources, IMAGE_SIZE)
    private var originOffsetX = 0f
    private var originOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var smallScale = 1f
    private var bigScale = 1f
    private var isBig = false
    private var currentScale = 0f
        set(value) {
            field = value
            invalidate()
        }
    private val overScroller = OverScroller(context)
    private val flingRunnable = object : Runnable {
        override fun run() {
            if (overScroller.computeScrollOffset()) {
                offsetX = overScroller.currX.toFloat()
                offsetY = overScroller.currY.toFloat()
                invalidate()
                postOnAnimation(this)
            }
        }
    }

    private val scaleAnim: ObjectAnimator by lazy(LazyThreadSafetyMode.NONE) {
        ObjectAnimator.ofFloat(this, "currentScale", smallScale, bigScale)
    }

    private var gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            isBig = !isBig
            if (isBig) {
                offsetX = -1 * (e.x - width / 2f) * (bigScale / smallScale - 1)
                offsetY = -1 * (e.y - height / 2f) * (bigScale / smallScale - 1)
                scaleAnim.start()
            } else {
                scaleAnim.reverse()
            }
            return true
        }

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            if (isBig) {
                offsetX -= distanceX
                offsetY -= distanceY
                fixOffsets()
                invalidate()
            }
            return false
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            return if (isBig) {
                overScroller.fling(
                    offsetX.toInt(),
                    offsetY.toInt(),
                    velocityX.toInt(),
                    velocityY.toInt(),
                    (-(bitmap.width * bigScale - width) / 2f).toInt(),
                    ((bitmap.width * bigScale - width) / 2f).toInt(),
                    (-(bitmap.height * bigScale - height) / 2f).toInt(),
                    ((bitmap.height * bigScale - height) / 2f).toInt(),
                    40.dp.toInt(),
                    40.dp.toInt()
                )
                postOnAnimation(flingRunnable)
                true
            } else {
                false
            }
        }
    })
    private var scaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val tempScale = currentScale * detector.scaleFactor
            return if (tempScale <= smallScale || tempScale > bigScale) {
                false
            } else {
                currentScale = tempScale
                true
            }

        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            offsetX = -1 * (detector.focusX - width / 2f) * (bigScale / smallScale - 1)
            offsetY = -1 * (detector.focusY - height / 2f) * (bigScale / smallScale - 1)
            return true
        }
    })

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        originOffsetX = (width - bitmap.width) / 2f
        originOffsetY = (height - bitmap.height) / 2f
        val wScale = width / bitmap.width.toFloat()
        val hScale = height / bitmap.height.toFloat()
        if (wScale > hScale) {
            bigScale = EXTRA_SCALE_FACTOR * wScale
            smallScale = hScale
        } else {
            bigScale = EXTRA_SCALE_FACTOR * hScale
            smallScale = wScale
        }
        currentScale = smallScale
        isBig = false
        scaleAnim.setFloatValues(smallScale, bigScale)
    }

    override fun onDraw(canvas: Canvas) {
        val scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        canvas.scale(currentScale, currentScale, (width / 2).toFloat(), (height / 2).toFloat())
        canvas.drawBitmap(bitmap, originOffsetX, originOffsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress) {
            gestureDetector.onTouchEvent(event)
        }
        return true
    }

    fun fixOffsets() {
        offsetX = offsetX.coerceAtLeast((width - bitmap.width * bigScale) / 2).coerceAtMost((bitmap.width * bigScale - width) / 2)
        offsetY = offsetY.coerceAtLeast((height - bitmap.height * bigScale) / 2).coerceAtMost((bitmap.height * bigScale - height) / 2)
    }
}