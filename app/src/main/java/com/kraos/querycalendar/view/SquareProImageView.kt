package com.kraos.querycalendar.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

/**
 * @author kraos
 * @date 2025/3/26
 * @desc 保证正方形的ImageVIew
 */
class SquareProImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

//    override fun layout(l: Int, t: Int, r: Int, b: Int) {
//        val minSize = min(measuredWidth, measuredHeight)
//        super.layout(l, t, l + 100, t + 100)
//    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val minSize = min(measuredWidth, measuredHeight)
        setMeasuredDimension(minSize, minSize)
    }

}