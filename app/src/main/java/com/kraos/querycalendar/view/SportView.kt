package com.kraos.querycalendar.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Xfermode
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.createBitmap
import androidx.core.graphics.toColorInt
import com.kraos.querycalendar.R
import com.kraos.querycalendar.util.px

/**
 * @author kraos
 * @date 2025/3/20
 * @desc 仿运动手环的View
 */
class SportView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(
    context, attrs, defStyleAttr
) {
    private val paint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 20f.px
    }
    private val textPaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeCap = Paint.Cap.ROUND
        textSize = 40f.px
        textAlign = Paint.Align.CENTER
        //设置文字的背景色
    }
    private val radius = 150f.px
    private lateinit var processRect: RectF
    private val textFontMetrics = textPaint.getFontMetrics()
    private val textBounds = Rect()

    //随机生成4个字母绘制
    private fun random4Letters(): String {
        // 创建包含所有大小写字母的字符池
        val letters = ('A'..'Z') + ('a'..'z')
        // 生成 4 个随机字符并拼接
        return (1..6).map { letters.random() }.joinToString("")
    }

    private val handler = Handler(Looper.getMainLooper())
    private val updateTextRunnable = object : Runnable {
        override fun run() {
            invalidate()
            handler.postDelayed(this, 500)
        }
    }

    init {
        handler.post(updateTextRunnable)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        processRect = RectF(
            width.toFloat() / 2f - radius,
            height.toFloat() / 2f - radius,
            width.toFloat() / 2f + radius,
            height.toFloat() / 2f + radius
        )
    }

    override fun onDraw(canvas: Canvas) {
        val currentText = random4Letters()
        //画背景进度圆
        paint.color = "#808080".toColorInt()
        canvas.drawCircle(width.toFloat() / 2f, height.toFloat() / 2f, radius, paint)

        //进度弧
        paint.color = "#ffc0cb".toColorInt()
        canvas.drawArc(processRect, -90f, 250f, false, paint)

        //每次随机生成4个字母
        textPaint.color = "#ffc0cb".toColorInt()
        textPaint.getTextBounds(currentText, 0, currentText.length, textBounds)
        canvas.drawText(
            random4Letters(),
            width / 2f,
            height / 2f - (textFontMetrics.ascent + textFontMetrics.descent) / 2f,
//            height / 2f - (textBounds.top + textBounds.bottom) / 2f,
            textPaint
        )

    }


}