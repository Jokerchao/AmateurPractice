package com.kraos.querycalendar.view

import android.content.ClipData
import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * @author kraos
 * @date 2025/4/9
 */
class DragToCollectLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
//    private lateinit var binding: ActivityDragToCollectBinding

    private val dragListener = CustomDragListener()

    override fun onFinishInflate() {
        super.onFinishInflate()
        findViewById<ImageView>(com.kraos.querycalendar.R.id.image).apply {
            setOnDragListener(dragListener)
            setOnLongClickListener {
                val dragShadowBuilder = DragShadowBuilder(this)
                val dragData = ClipData.newPlainText("dragData", contentDescription)
                startDragAndDrop(dragData, dragShadowBuilder, this, 0)
            }
        }
        findViewById<LinearLayout>(com.kraos.querycalendar.R.id.ll_container).setOnDragListener(
            dragListener
        )
    }

    inner class CustomDragListener : OnDragListener {
        override fun onDrag(v: View?, event: DragEvent?): Boolean {
            when (event?.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    // Do something when drag starts
                }

                DragEvent.ACTION_DRAG_ENTERED -> {
                    // Do something when drag enters the view
                }

                DragEvent.ACTION_DRAG_EXITED -> {
                    // Do something when drag exits the view
                }

                DragEvent.ACTION_DROP -> {
                    // Handle the drop event
                    if (v is LinearLayout) {
                        TextView(context).apply {
                            text = event.clipData.getItemAt(0).text
                            textSize = 20f
                            layoutParams = LayoutParams(
                                LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT
                            )
                            v.addView(this)
                        }

                    }
                }
            }
            return true
        }
    }

}