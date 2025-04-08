package com.kraos.querycalendar.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import androidx.core.util.size
import com.kraos.querycalendar.util.px

/**
 * @author kraos
 * @date 2025/4/7
 * @desc 原始方式的多点触控 分开处理
 */
class MultiTouchView3 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "MultiTouchView2"
    }

    //用来记录每个path和pointerId的映射
    private val paths = SparseArray<Path>()

    private val paint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 5f.px
        strokeJoin = Paint.Join.ROUND
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //遍历path
        for (index in 0 until paths.size) {
            canvas.drawPath(paths.valueAt(index), paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val pointerIndex = event.actionIndex
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_POINTER_DOWN -> {
                val newPath = Path()
                //初始化path的起点
                newPath.moveTo(event.getX(pointerIndex), event.getY(pointerIndex))
                paths.append(event.getPointerId(pointerIndex), newPath)
                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {
                //遍历所有的move更新path
                for (index in 0 until paths.size) {
                    val pointerId = paths.keyAt(index)
                    val moveX = event.getX(index)
                    val moveY = event.getY(index)
                    paths.get(pointerId).lineTo(moveX, moveY)
                    invalidate()
                }

            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_POINTER_UP -> {
                val pointerId = event.getPointerId(pointerIndex)
                //重置path
                paths.get(pointerId).reset()
                paths.remove(pointerId)
                invalidate()
            }
        }

        return true
    }

}