package com.kraos.querycalendar.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View
import com.kraos.querycalendar.R
import com.kraos.querycalendar.util.px
import java.util.jar.Attributes
import androidx.core.graphics.withTranslation
import androidx.core.graphics.withClip

/**
 * kotlin实现的camera view
 */
class CameraProView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(
    context, attrs, defStyleAttr
) {
    companion object {
        private val IMAGE_WIDTH = 150f.px
        private val IMAGE_PADDING = 50f.px
    }

    private val paint = Paint(ANTI_ALIAS_FLAG)
    private val camera = Camera().apply {
        rotateX(30f)
        setLocation(0f, 0f, -8f * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas) {
        //裁掉下半部分
        canvas.withTranslation(
            (IMAGE_PADDING + IMAGE_WIDTH / 2f),
            (IMAGE_PADDING + IMAGE_WIDTH / 2f)
        ) {
            rotate(-10f)
            camera.applyToCanvas(this)
            clipRect(
                -IMAGE_WIDTH,
                0f,
                IMAGE_WIDTH,
                IMAGE_WIDTH
            )
            rotate(10f)
            translate(-(IMAGE_PADDING + IMAGE_WIDTH / 2f), -(IMAGE_PADDING + IMAGE_WIDTH / 2f))
            drawBitmap(getAvatar(IMAGE_WIDTH.toInt()), IMAGE_PADDING, IMAGE_PADDING, paint)
        }


        //正常画上半部分
        canvas.withTranslation(
            (IMAGE_PADDING + IMAGE_WIDTH / 2f),
            (IMAGE_PADDING + IMAGE_WIDTH / 2f)
        ) {
            rotate(-10f)
            clipRect(
                -IMAGE_WIDTH,
                -IMAGE_WIDTH,
                IMAGE_WIDTH,
                0f
            )
            rotate(10f)
            translate(
                -(IMAGE_PADDING + IMAGE_WIDTH / 2f),
                -(IMAGE_PADDING + IMAGE_WIDTH / 2f)
            )
            drawBitmap(getAvatar(IMAGE_WIDTH.toInt()), IMAGE_PADDING, IMAGE_PADDING, paint)
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