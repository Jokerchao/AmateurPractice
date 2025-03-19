package com.kraos.querycalendar.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style.STROKE
import android.graphics.Path
import android.graphics.Path.Direction
import android.graphics.PathDashPathEffect
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import com.kraos.querycalendar.util.px
import kotlin.math.cos
import kotlin.math.sin

//开口的角度
private const val OPEN_ANGEL = 140f

//仪表半径
private val RADIUS = 120f.px

private val STROKE_WIDTH = 3f.px

private val dashWidth = 2f.px

private val dashHeight = 8f.px

private const val startAngel = OPEN_ANGEL / 2 + 90

private val MARK = 5

private val MARK_LENGTH = 100f.px


class DashboardProView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(
    context, attrs, defStyleAttr
) {


    private val canvas = Canvas()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = STROKE
        strokeWidth = STROKE_WIDTH
    }
    private var coordinateOriginX: Float = 0f
    private var coordinateOriginY: Float = 0f
    private val path: Path = Path()
    private val dashPath: Path = Path()
    private lateinit var pathDashPathEffect: PathDashPathEffect

    init {
        dashPath.addRect(0f, 0f, dashWidth, dashHeight, Direction.CCW)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        coordinateOriginX = w / 2f
        coordinateOriginY = h / 2f
        path.apply {
            path.reset()
            addArc(
                coordinateOriginX - RADIUS,
                coordinateOriginY - RADIUS,
                coordinateOriginX + RADIUS,
                coordinateOriginY + RADIUS,
                startAngel,
                360 - OPEN_ANGEL,
            )
        }
        val pathMeasure = PathMeasure(path, false)

        pathDashPathEffect = PathDashPathEffect(
            dashPath,
            (pathMeasure.length - dashWidth) / 20f,
            0f,
            PathDashPathEffect.Style.ROTATE
        )

    }

    override fun onDraw(canvas: Canvas) {
        //画弧
        canvas.drawPath(
            path,
            paint
        )

        paint.pathEffect = pathDashPathEffect
        //画虚线弧
        canvas.drawPath(path, paint)
        paint.pathEffect = null

        //画指针
        canvas.drawLine(
            coordinateOriginX,
            coordinateOriginY,
            coordinateOriginX + getLinePoint(MARK).first.toFloat(),
            coordinateOriginY + getLinePoint(MARK).second.toFloat(),
            paint
        )

    }

    /**
     * 根据角度获取坐标
     */
    fun getLinePoint(mark: Int): Pair<Double, Double> {
        val pair = Pair<Double, Double>(
            cos(Math.toRadians(((360 - OPEN_ANGEL) / 20f * mark + startAngel).toDouble())) * MARK_LENGTH,
            sin(Math.toRadians(((360 - OPEN_ANGEL) / 20f * mark + startAngel).toDouble())) * MARK_LENGTH
        )
        return pair
    }


}