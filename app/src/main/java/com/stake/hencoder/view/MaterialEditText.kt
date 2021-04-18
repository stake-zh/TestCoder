package com.stake.hencoder.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.stake.hencoder.dp

private val TEXT_SIZE = 12.dp
private val TEXT_MARGIN = 8.dp
private val HORIZONTAL_OFFSET = 5.dp
private val VERTICAL_OFFSET = 23.dp
private val EXTRA_VERTICAL_OFFSET = 16.dp

class MaterialEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = TEXT_SIZE
    }
    private var floatingLabelShown = false
    var floatingLabelFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val animator by lazy {
        ObjectAnimator.ofFloat(this, "floatingLabelFraction", 0f, 1f)
    }

    init {
        setPadding(paddingLeft, (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(), paddingRight, paddingBottom)
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        if (floatingLabelShown && text.isNullOrEmpty()) {
            floatingLabelShown = false
            animator.reverse()
        } else if (!floatingLabelShown && !text.isNullOrEmpty()) {
            floatingLabelShown = true
            animator.start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.alpha = (0xff * floatingLabelFraction).toInt()
        val currentVerticalOffset = VERTICAL_OFFSET + EXTRA_VERTICAL_OFFSET * (1 - floatingLabelFraction)
        canvas.drawText(hint.toString(), HORIZONTAL_OFFSET, currentVerticalOffset, paint)
    }
}