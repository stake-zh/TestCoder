package com.stake.hencoder

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp
    get() = this.toFloat().dp

fun getAvatar(resources: Resources = Resources.getSystem(), width: Int): Bitmap {
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }
    BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    options.apply {
        inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
    }
    return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
}