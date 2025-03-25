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
    private var radius = 100f.px
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(width / 2f, height / 2f, radius, paint)
    }

}