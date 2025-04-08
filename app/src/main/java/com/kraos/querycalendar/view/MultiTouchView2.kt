package com.kraos.querycalendar.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.kraos.querycalendar.util.Util
import com.kraos.querycalendar.util.px

/**
 * @author kraos
 * @date 2025/4/7
 * @desc 原始方式的多点触控 配合型
 */
class MultiTouchView2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "MultiTouchView2"
    }

    private val bimap = Util.getAvatar(resources, 300f.px.toInt())
    private val paint = Paint(ANTI_ALIAS_FLAG)
    private var offsetX = 0f
    private var offsetY = 0f

    private var focusX = 0f
    private var focusY = 0f

    private var downX = 0f
    private var downY = 0f

    //原始偏移坐标
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    //当前目标移动的pointerId

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bimap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //计算中心焦点
        var sumX = 0f
        var sumY = 0f
        val isPointerUp = event.actionMasked == MotionEvent.ACTION_POINTER_UP
        for (index in 0 until event.pointerCount) {
            if (!(isPointerUp && index == event.actionIndex)) {
                sumX += event.getX(index)
                sumY += event.getY(index)
            }
        }
        val totalPointer = if (isPointerUp) {
            event.pointerCount - 1
        } else {
            event.pointerCount
        }
        focusX = sumX / totalPointer
        focusY = sumY / totalPointer


        when (event.actionMasked) {

            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_POINTER_UP -> {
                Log.d(
                    TAG,
                    "ACTION_DOWN pointerCount = ${event.pointerCount} pointerIndex = ${event.actionIndex} pointerId = ${
                        event.getPointerId(event.actionIndex)
                    }"
                )
                // 处理单点触控
                downX = focusX
                downY = focusY
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }


            MotionEvent.ACTION_MOVE -> {
                Log.d(
                    TAG,
                    "ACTION_MOVE pointerCount = ${event.pointerCount} pointerIndex = ${event.actionIndex} pointerId = ${
                        event.getPointerId(event.actionIndex)
                    }"
                )
                // 处理触摸移动
                offsetX = focusX - downX + originalOffsetX
                offsetY = focusY - downY + originalOffsetY
                invalidate()
            }

        }
        return true
    }

}