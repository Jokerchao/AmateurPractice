package com.kraos.querycalendar.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.core.view.children
import kotlin.math.abs

/**
 * @author kraos
 * @date 2025/4/9
 * @description : 简单的Viewpager实现
 */
class TwoPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "TwoPager"
    }

    //用于滚动的OverScroller
    private val overScroller = OverScroller(context)

    //获取触摸滑动的最小距离
    private val touchSlop = ViewConfiguration.get(context).scaledPagingTouchSlop

    //获取最大滑动速度
    private val maximumFlingVelocity = ViewConfiguration.get(context).scaledMaximumFlingVelocity

    //获取最小滑动速度
    private val minimumFlingVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity

    //初始化速度侦查器
    private val velocityTracker = VelocityTracker.obtain()

    private var downX = 0f
    private var downY = 0f

    //记录已经产生的滑动距离
    private var downScrollX = 0f

    private var isScrolling = false


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //硬编码子View 只做演示
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        var left = 0
        var childEnd = width
        val childBottom = height
        for (child in children) {
            child.layout(left, 0, childEnd, childBottom)
            left += width
            childEnd += width
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(ev)
        var result = false
        when (ev?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
                downScrollX = scrollX.toFloat()
                isScrolling = false
            }

            MotionEvent.ACTION_MOVE -> if (!isScrolling) {
                val dx = ev.x - downX
                Log.d(TAG, "onInterceptTouchEvent: $dx downX $downX downScrollX $downScrollX")
                if (abs(dx) > touchSlop) {
                    isScrolling = true
                    //横向滑动
                    result = true
                    parent.requestDisallowInterceptTouchEvent(true)
                }
            }
        }

        return result
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(ev)

        when (ev?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
                downScrollX = scrollX.toFloat()
            }

            MotionEvent.ACTION_MOVE -> {
                val dx = ev.x - downX - downScrollX
                //限制dx的移动范围
                val coerceIn = dx.coerceIn(-width.toFloat(), 0f).toInt()
                Log.d(TAG, "onTouchEvent: $coerceIn")
                scrollTo(-coerceIn, 0)
            }

            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000, maximumFlingVelocity.toFloat())
                val xVelocity = velocityTracker.xVelocity
                val targetPage = if (abs(xVelocity) < minimumFlingVelocity) {
                    //如果速度小于最小速度，找到最近的page滚动
                    if (scrollX > width / 2) {
                        1
                    } else {
                        0
                    }
                } else {
                    if (xVelocity > 0) {
                        0
                    } else {
                        1
                    }
                }
                Log.d(TAG, "onTouchEvent: $xVelocity targetPage $targetPage")
                //松手后按照当前速度滑动
                overScroller.startScroll(
                    scrollX,
                    0,
                    targetPage * width - scrollX,
                    0
                )
                postInvalidateOnAnimation()
            }
        }
        return true
    }

    override fun computeScroll() {
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.currX, overScroller.currY)
            postInvalidateOnAnimation()
        }
    }
}