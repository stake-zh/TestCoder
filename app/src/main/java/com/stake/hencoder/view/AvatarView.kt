package com.stake.hencoder.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.stake.hencoder.R
import com.stake.hencoder.dp

private val IMAGE_WITH = 200.dp
private val IMAGE_PADDING = 20.dp

class AvatarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private val bounds = RectF(
        IMAGE_PADDING,
        IMAGE_PADDING,
        IMAGE_PADDING + IMAGE_WITH,
        IMAGE_PADDING + IMAGE_WITH,
    )

    override fun onDraw(canvas: Canvas) {
        val saveLayer = canvas.saveLayer(bounds, null)
        canvas.drawOval(bounds, paint)
        paint.xfermode = xfermode
        canvas.drawBitmap(getAvatar(IMAGE_WITH.toInt()), IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restoreToCount(saveLayer)
    }

    private fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    }
}