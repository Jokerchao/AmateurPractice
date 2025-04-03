package com.kraos.querycalendar.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.OnDoubleTapListener
import android.view.GestureDetector.OnGestureListener
import android.view.MotionEvent
import android.view.View
import com.kraos.querycalendar.util.Util
import com.kraos.querycalendar.util.px

/**
 * @author kraos
 * @date 2025/4/3
 * 双指放缩的图片
 */
class ScalableProImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(
    context, attrs, defStyleAttr
), OnGestureListener, OnDoubleTapListener {
    //图片宽度
    private var imageWidth = 370f.px
    private val bitmap = Util.getAvatar(resources, imageWidth.toInt())
    private val imageHeight = bitmap.height
    private var originOffsetX = 0f
    private var originOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private val paint = Paint(ANTI_ALIAS_FLAG)
    var scaleFraction = 1f

    //放大系数
    var smallScale = 0f
    var bigScale = 0f
    var currentScale = 0f
        set(value) {
            field = value
            invalidate()
        }

    //手势监听器
    private val gestureDetector = GestureDetector(context, this).apply {
        setOnDoubleTapListener(this@ScalableProImageView)
    }

    //动画变化器
    private val scaleAnimator by lazy {
        ObjectAnimator.ofFloat(this, "currentScale", 0f).apply {
            setFloatValues(smallScale, bigScale)
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originOffsetY = (height - imageHeight) / 2f
        originOffsetX = (width - imageWidth) / 2f

        if (bitmap.width / imageHeight.toFloat() > width / height.toFloat()) {
            //Wide image
            smallScale = width / imageWidth
            bigScale = height / imageHeight.toFloat()
        } else {
            //narrow pic
            smallScale = height / imageHeight.toFloat()
            bigScale = width / imageWidth
        }
        currentScale = smallScale
    }

    override fun onDraw(canvas: Canvas) {
        //centerCrop的方式展示

        canvas.scale(
            currentScale,
            currentScale,
            width / 2f,
            height / 2f
        )
        canvas.drawBitmap(
            bitmap,
            originOffsetX,
            originOffsetY,
            paint
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent) {
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent) {

    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        return false
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        currentScale = if (currentScale == smallScale) {
            bigScale
        } else {
            smallScale
        }
        if (currentScale == bigScale) {
            scaleAnimator.start()
        } else {
            scaleAnimator.reverse()
        }
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        return false
    }


}