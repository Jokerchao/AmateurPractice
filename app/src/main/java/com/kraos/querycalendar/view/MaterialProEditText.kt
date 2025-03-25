package com.kraos.querycalendar.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.graphics.toColorInt
import com.kraos.querycalendar.R
import com.kraos.querycalendar.util.px
import androidx.core.content.withStyledAttributes


class MaterialProEditText constructor(
    context: Context, attrs: AttributeSet?
) : AppCompatEditText(context, attrs) {
    private val PADDING_TOP = 5f.px

    //顶部的文字尺寸
    private val TEXT_SIZE = 15f.px
    private val TEXT_VETICAL_OFFEX = 18f.px
    private val TEXT_HORIZANTAL_OFFEX = 4f.px
    private val EXTRA_OFFSET = 15f.px

    var showLabelFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val showAnimator by lazy {
        ObjectAnimator.ofFloat(this, "showLabelFraction", 0f, 1f)
    }

    var showLabel = false
        set(value) {
            if (field != value) {
                field = value
                invalidate()
                if (field) {
                    setPadding(
                        paddingStart,
                        (paddingTop + PADDING_TOP + TEXT_SIZE).toInt(),
                        paddingEnd,
                        paddingBottom
                    )
                } else {
                    setPadding(
                        paddingStart,
                        (paddingTop - PADDING_TOP - TEXT_SIZE).toInt(),
                        paddingEnd,
                        paddingBottom
                    )
                }
            }
        }

    var isLabelShown = false

    private val paint = Paint(ANTI_ALIAS_FLAG).apply {
        textSize = TEXT_SIZE
        color = "#FF00FF".toColorInt()
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.MaterialProEditText) {
            showLabel = getBoolean(0, true)
        }
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (isLabelShown && text.isNullOrEmpty()) {
            showAnimator.reverse()
            isLabelShown = false
        } else if (!isLabelShown && !text.isNullOrEmpty()) {
            showAnimator.start()
            isLabelShown = true
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.alpha = (showLabelFraction * 0xff).toInt()
        val currentOffset = TEXT_VETICAL_OFFEX + EXTRA_OFFSET * (1 - showLabelFraction)
        canvas.drawText(hint.toString(), TEXT_HORIZANTAL_OFFEX, currentOffset, paint)
    }

}