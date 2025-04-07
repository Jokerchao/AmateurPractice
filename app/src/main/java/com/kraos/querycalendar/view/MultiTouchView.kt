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
 * @desc 原始方式的多点触控 接力型
 */
class MultiTouchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "MultiTouchView"
    }

    private val bimap = Util.getAvatar(resources, 300f.px.toInt())
    private val paint = Paint(ANTI_ALIAS_FLAG)
    private var offsetX = 0f
    private var offsetY = 0f

    private var downX = 0f
    private var downY = 0f

    //原始偏移坐标
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    //当前目标移动的pointerId
    private var triggerPointerId = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bimap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(
                    TAG,
                    "ACTION_DOWN pointerCount = ${event.pointerCount} pointerIndex = ${event.actionIndex} pointerId = ${
                        event.getPointerId(event.actionIndex)
                    }"
                )
                // 处理单点触控
                downX = event.x
                downY = event.y
                originalOffsetX = offsetX
                originalOffsetY = offsetY
                triggerPointerId = event.getPointerId(0)
            }


            MotionEvent.ACTION_POINTER_DOWN -> {
                Log.d(
                    TAG,
                    "ACTION_POINTER_DOWN = ${event.pointerCount} pointerIndex = ${event.actionIndex} pointerId = ${
                        event.getPointerId(event.actionIndex)
                    }"
                )
                // 处理多点触控
                val actionIndex = event.actionIndex
                triggerPointerId = event.getPointerId(actionIndex)
                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)
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
                val index = event.findPointerIndex(triggerPointerId)
                // 处理触摸移动
                offsetX = event.getX(index) - downX + originalOffsetX
                offsetY = event.getY(index) - downY + originalOffsetY
                invalidate()
            }

            MotionEvent.ACTION_POINTER_UP -> {
                //抬起以后要找到接力这个手指的pointer
                val pointerIndex = event.findPointerIndex(event.actionIndex)
                //找最后一个手指接力
                val newIndex = if (pointerIndex == event.pointerCount - 1) {
                    event.pointerCount - 2
                } else {
                    event.pointerCount - 1
                }
                triggerPointerId = event.getPointerId(newIndex)
                downX = event.getX(newIndex)
                downY = event.getY(newIndex)
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }


        }
        return true
    }

}