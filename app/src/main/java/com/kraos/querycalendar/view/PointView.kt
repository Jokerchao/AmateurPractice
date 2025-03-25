package com.kraos.querycalendar.view

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toColorInt
import com.kraos.querycalendar.util.px

/**
 * @author kraos
 * @date 2025/3/24
 * @desc 一个简单的坐标点自定义View
 */
class PointView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint(ANTI_ALIAS_FLAG).apply {
        color = "#FFFFFF00".toColorInt()
        strokeWidth = 10f.px
    }
    var position = PointF(50f.px, 50f.px)
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPoint(position.x, position.y, paint)

    }

    class PointEvaluator : TypeEvaluator<PointF> {
        override fun evaluate(
            fraction: Float,
            startValue: PointF,
            endValue: PointF
        ): PointF? {
//            newValue = startValue + fraction * (endValue - startValue)
            val newValueX = startValue.x + fraction * (endValue.x - startValue.x)
            val newValueY = startValue.y + fraction * (endValue.y - startValue.y)
            return PointF(newValueX, newValueY)
        }

    }


}