package com.kraos.querycalendar.view

import android.content.Context
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import com.kraos.querycalendar.util.px
import kotlin.math.max

/**
 * 自定义测量的布局
 */
class TagProLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewGroup(
    context,
    attrs
) {
    //记录测量出来的每个子View的尺寸
    private val childBounds = mutableListOf<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        //当前行已经使用的宽度
        var lineWidthUsed = 0
        //当前总共已经使用的高度
        var lineHeightUsed = 0
        //当前行最大的高度
        var lineMaxWidth = 0
        //当前行最大的高度
        var lineMaxHeight = 0
        for ((index, child) in children.withIndex()) {
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, lineHeightUsed)

            //如果超出宽度换行，需要从第二行开始
            if (specWidthMode != MeasureSpec.UNSPECIFIED && child.measuredWidth + lineWidthUsed > specWidthSize) {
                lineHeightUsed = lineMaxHeight + lineHeightUsed
                lineMaxWidth = max(lineWidthUsed, lineMaxWidth)
                lineWidthUsed = 0
                lineMaxHeight = 0
                measureChildWithMargins(
                    child,
                    widthMeasureSpec,
                    lineWidthUsed,
                    heightMeasureSpec,
                    lineHeightUsed
                )
            }

            if (childBounds.size <= index) {
                childBounds.add(Rect())
            }
            childBounds[index].set(
                lineWidthUsed,
                lineHeightUsed,
                lineWidthUsed + child.measuredWidth,
                lineHeightUsed + child.measuredHeight
            )
            lineWidthUsed += child.measuredWidth
            lineMaxHeight = max(child.measuredHeight, lineMaxHeight)
        }

        //测量自己的宽高，宽度还要考虑最后一行
        val widthParent = max(lineMaxWidth, lineWidthUsed)
        val heightParent = lineMaxHeight + lineHeightUsed

        //设置自己的尺寸
        setMeasuredDimension(widthParent, heightParent)
    }

    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        //上面测量保存好了，这里直接绘制
        for ((index, child) in children.withIndex()) {
            val rect = childBounds[index]
            child.layout(rect.left, rect.top, rect.right, rect.bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams? {
        return MarginLayoutParams(context, attrs)
    }


}