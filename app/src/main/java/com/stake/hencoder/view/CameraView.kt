package com.stake.hencoder.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.stake.hencoder.R
import com.stake.hencoder.dp

private val BITMAP_SIZE = 200.dp
private val BITMAP_PADDING = 100.dp

class CameraView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val camera = Camera().apply {
        rotateX(30f)
        setLocation(0f, 0f, -6f * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2)
        canvas.clipRect(-BITMAP_SIZE / 2, -BITMAP_SIZE / 2, BITMAP_SIZE / 2, 0f)
        canvas.translate(-BITMAP_PADDING - BITMAP_SIZE / 2, -BITMAP_PADDING - BITMAP_SIZE / 2)
        canvas.drawBitmap(getBitmap(BITMAP_SIZE.toInt()), BITMAP_PADDING, BITMAP_PADDING, paint)
        canvas.restore()

        canvas.save()
        canvas.translate(BITMAP_PADDING + BITMAP_SIZE / 2, BITMAP_PADDING + BITMAP_SIZE / 2)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-BITMAP_SIZE / 2, 0f, BITMAP_SIZE / 2, BITMAP_SIZE / 2)
        canvas.translate(-BITMAP_PADDING - BITMAP_SIZE / 2, -BITMAP_PADDING - BITMAP_SIZE / 2)
        canvas.drawBitmap(getBitmap(BITMAP_SIZE.toInt()), BITMAP_PADDING, BITMAP_PADDING, paint)
        canvas.restore()
    }

    private fun getBitmap(width: Int): Bitmap {
        val option = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, option)
        option.apply {
            inJustDecodeBounds = false
            inDensity = outWidth
            inTargetDensity = width
        }
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, option)
    }

}