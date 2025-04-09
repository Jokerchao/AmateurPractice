package com.kraos.querycalendar.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.customview.widget.ViewDragHelper
import kotlin.math.abs

/**
 * @author kraos
 * @date 2025/4/9
 */
class DragHelperLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    //最小滑动触发速度
    private val minVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity

    private val dragHelper = ViewDragHelper.create(this, DragHelperCallBack())


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event)
        return true
    }

    inner class DragHelperCallBack : ViewDragHelper.Callback() {
        override fun tryCaptureView(
            child: View,
            pointerId: Int
        ): Boolean {
            return true
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            if (abs(yvel) > minVelocity) {
                if (yvel > 0) {
                    dragHelper.settleCapturedViewAt(0, height - releasedChild.height)
                } else {
                    dragHelper.settleCapturedViewAt(0, 0)
                }
            } else {
                //上面的距离比下面的距离大就向下滑动
                if (releasedChild.top < height - releasedChild.bottom) {
                    dragHelper.settleCapturedViewAt(0, 0)
                } else {
                    dragHelper.settleCapturedViewAt(0, height - releasedChild.height)
                }
            }
            postInvalidateOnAnimation()
        }
    }

    override fun computeScroll() {
        if (dragHelper.continueSettling(true)) {
            postInvalidateOnAnimation()
        }
    }

}