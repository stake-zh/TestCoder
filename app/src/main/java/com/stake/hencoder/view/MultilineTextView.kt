package com.stake.hencoder.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.stake.hencoder.R
import com.stake.hencoder.dp

private val IMAGE_SIZE = 150.dp
private val IMAGE_PADDING = 50.dp

class MultilineTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    val content =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tempor pretium felis in mattis. Vestibulum pellentesque, mauris vel commodo suscipit, magna ipsum interdum massa, sit amet rutrum sapien odio ut turpis. Curabitur venenatis mi ut sapien molestie, eget vulputate lectus dignissim. In hac habitasse platea dictumst. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Etiam et ultricies nulla. Aliquam erat volutpat. Morbi ornare tincidunt justo, vitae euismod augue fermentum ac. Nam vitae nibh venenatis, dictum tellus id, finibus justo. Nulla consequat sem sit amet felis blandit dapibus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas."

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16.dp
    }
    private val floatArrayOf = floatArrayOf(0f)
    private val fontMetrics = Paint.FontMetrics()

    init {
        paint.getFontMetrics(fontMetrics)
    }

    // private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
    //     textSize = 16.dp
    // }
    // private lateinit var staticLayout: StaticLayout

    /*override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        staticLayout = StaticLayout.Builder.obtain(content, 0, content.length, textPaint, width).build()
    }*/

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // staticLayout.draw(canvas)

        canvas.drawBitmap(getBitmap(IMAGE_SIZE.toInt()), width - IMAGE_SIZE, IMAGE_PADDING, paint)
        var count = 0;
        var verticalOffset = -fontMetrics.top
        while (count < content.length) {
            val lineWidth = if (verticalOffset + fontMetrics.bottom < IMAGE_PADDING || verticalOffset + fontMetrics.top > IMAGE_PADDING + IMAGE_SIZE) {
                width.toFloat()
            } else {
                width.toFloat() - IMAGE_SIZE
            }
            val currentLineCount = paint.breakText(content, count, content.length, true, lineWidth, floatArrayOf)
            canvas.drawText(content, count, count + currentLineCount, 0f, verticalOffset, paint)
            count += currentLineCount
            verticalOffset += paint.fontSpacing
        }

    }

    private fun getBitmap(width: Int): Bitmap {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
        options.apply {
            inJustDecodeBounds = false
            inDensity = options.outHeight
            inTargetDensity = width
        }
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    }
}