package com.kraos.querycalendar.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Xfermode
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.createBitmap
import androidx.core.graphics.toColorInt
import com.kraos.querycalendar.R
import com.kraos.querycalendar.util.px

/**
 * @author kraos
 * @date 2025/3/20
 */
class TransModeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(
    context, attrs, defStyleAttr
) {
    private val paint = Paint(ANTI_ALIAS_FLAG)
    private val circleBitmap = createBitmap(150f.px.toInt(), 150f.px.toInt())
    private val rectBitmap = createBitmap(150f.px.toInt(), 150f.px.toInt())
    private val xfermode: Xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)

    init {
        val canvas = Canvas(circleBitmap)
        paint.color = "#FF0000".toColorInt()
        canvas.drawOval(50f.px, 0f.px, 150f.px, 100f.px, paint)
        paint.color = "#00FF00".toColorInt()
        canvas.setBitmap(rectBitmap)
        canvas.drawRect(0f.px, 50f.px, 100f.px, 150f.px, paint)
    }

    override fun onDraw(canvas: Canvas) {
        //切头像
//        val count = canvas.saveLayer(0f.px, 0f.px, 300f.px, 300f.px, null)
//        canvas.drawOval(150f.px, 50f.px, 300f.px, 200f.px, paint)
//        paint.xfermode = xfermode
//        canvas.drawBitmap(getAvatar(150f.px.toInt()), 150f.px, 50f.px, paint)
//        paint.xfermode = null
//        canvas.restoreToCount(count)

        //官网的xferMode展示
        val count = canvas.saveLayer(0f.px, 0f.px, 300f.px, 300f.px, null)
        canvas.drawBitmap(circleBitmap, 150f.px, 50f.px, paint)
        paint.xfermode = xfermode
        canvas.drawBitmap(rectBitmap, 150f.px, 50f.px, paint)
        paint.xfermode = null
        canvas.restoreToCount(count)
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