package com.kraos.querycalendar.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toColorInt
import com.kraos.querycalendar.util.px

/**
 * @author kraos
 * @date 2025/3/24
 */
class CircleProView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint(ANTI_ALIAS_FLAG).apply {
        color = "#FF0000".toColorInt()
    }

    private val topPadding = 100f.px
    private val startPadding = 100f.px

    var radius = 100f.px
        set(value) {
            field = value
            invalidate()
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = startPadding + 2 * radius
        val height = topPadding + 2 * radius
        val resolveWidth = resolveSize(width.toInt(), widthMeasureSpec)
        val resolveHeight = resolveSize(height.toInt(), heightMeasureSpec)
        setMeasuredDimension(resolveWidth, resolveHeight)
    }

    override fun onDraw(canvas: Canvas) {
//        canvas.drawCircle(width / 2f, height / 2f, radius, paint)
        canvas.drawOval(
            startPadding,
            topPadding,
            startPadding + 2 * radius,
            topPadding + 2 * radius,
            paint
        )
    }

}