package com.kraos.querycalendar.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toColorInt
import com.kraos.querycalendar.R
import com.kraos.querycalendar.util.px

private val imageWidth = 150f.px
private val imageTopPadding = 50f.px

class MultilineTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(
    context, attrs, defStyleAttr
) {
    private val paint = Paint(ANTI_ALIAS_FLAG)
    private val textPaint = Paint(ANTI_ALIAS_FLAG).apply {
        textSize = 15f.px
        color = "#FFFFFF".toColorInt()
        //设置文字的背景色
    }

    private val textFontMetrics = textPaint.getFontMetrics()
    private val measureFloats = floatArrayOf(0f)
    private val text: String =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum et mollis diam. Sed posuere turpis vel urna scelerisque ultricies. Nam est velit, dictum vitae maximus nec, laoreet eu turpis. Etiam vehicula, eros in vestibulum sagittis, velit ex efficitur ipsum, sed placerat sapien enim sed elit. Pellentesque dignissim, felis quis aliquet euismod, velit ligula rhoncus justo, in consectetur arcu arcu in sapien. Integer posuere elit eu turpis consectetur dictum. Donec gravida blandit ligula, eu accumsan sem tincidunt a. Mauris sagittis laoreet lectus quis tristique. Vestibulum tincidunt efficitur arcu vel viverra. Nunc dictum tortor ac volutpat semper. Fusce pulvinar ligula vel purus mollis interdum. Integer eu maximus est, nec mollis nisl. Phasellus et ex rhoncus, dapibus sapien vel, tempor nisl. Donec quis nisi ante. Vestibulum efficitur ipsum augue, quis suscipit lacus viverra sit amet. Pellentesque accumsan neque quam."
    private var startIndex = 0
    private var textTopPadding = -textFontMetrics.top
    private var textUesWidth = 0f

    override fun onDraw(canvas: Canvas) {
        //StaticLayout自动处理换行
//        val staticLayout = StaticLayout.Builder.obtain(text, 0, text.length, textPaint, width)
//            .build()
//        staticLayout.draw(canvas)

        //图片
        canvas.drawBitmap(getAvatar(imageWidth.toInt()), width - imageWidth, imageTopPadding, paint)

        //测量画文字绕开图片
        while (startIndex < text.length) {
            //在该区域时需要绕开图片
            textUesWidth =
                if (textTopPadding + textFontMetrics.bottom > imageTopPadding
                    && textTopPadding + textFontMetrics.top < imageTopPadding + imageWidth
                ) {
                    width.toFloat() - imageWidth
                } else {
                    width.toFloat()
                }
            val count =
                textPaint.breakText(
                    text,
                    startIndex,
                    text.length,
                    true,
                    textUesWidth.toFloat(),
                    measureFloats
                )
            canvas.drawText(
                text,
                startIndex,
                startIndex + count,
                0f,
                textTopPadding,
                textPaint
            )
            startIndex += count
            textTopPadding += textPaint.fontSpacing
        }
    }

    fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    }


}