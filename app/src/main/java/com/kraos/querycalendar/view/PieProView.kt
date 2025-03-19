package com.kraos.querycalendar.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathDashPathEffect
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import com.kraos.querycalendar.util.px
import kotlin.math.cos
import kotlin.math.sin
import androidx.core.graphics.toColorInt

private val RADIUS = 140f.px

private val OFFSET_LENGTH = 20f.px

class PieProView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(
    context, attrs, defStyleAttr
) {
    private val canvas = Canvas()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private var coordinateOriginX: Float = 0f
    private var coordinateOriginY: Float = 0f
    private var starAngel = 0f
    private val markIndex = 2

    //饼图角度
    private val angels = listOf(50f, 40f, 100f, 170f)
    private val colors = listOf(
        "#FF0000".toColorInt(),
        "#00FF00".toColorInt(),
        "#0000FF".toColorInt(),
        "#FFFF00".toColorInt()
    )


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        coordinateOriginX = w / 2f
        coordinateOriginY = h / 2f
    }

    override fun onDraw(canvas: Canvas) {
        for ((index, angel) in angels.withIndex()) {
            if (index == markIndex) {
                canvas.save()
                canvas.translate(
                    OFFSET_LENGTH * cos(Math.toRadians((starAngel + angel / 2).toDouble())).toFloat(),
                    OFFSET_LENGTH * sin(Math.toRadians((starAngel + angel / 2).toDouble())).toFloat()
                )
            }
            canvas.drawArc(
                coordinateOriginX - RADIUS,
                coordinateOriginY - RADIUS,
                coordinateOriginX + RADIUS,
                coordinateOriginY + RADIUS,
                starAngel,
                angel,
                true,
                paint.apply {
                    color = colors[index]
                }
            )
            starAngel += angel
            if (index == markIndex) {
                canvas.restore()
            }
        }
    }



}