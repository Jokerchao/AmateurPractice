package com.kraos.querycalendar.activity

import android.os.Bundle
import android.widget.ImageView
import com.kraos.querycalendar.R

/**
 * author: Kraos
 * date: 2023/2/24
 * description: 测试可拖拽view
 */
class TestDragViewActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_drag_view)
        //可拖动View
//        val dragView = findViewById<ImageView>(R.id.drag_view)
    }
}