package com.kraos.querycalendar.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.kraos.querycalendar.R
import com.kraos.querycalendar.databinding.ActivityTestDragViewBinding
import com.kraos.querycalendar.fragment.TestOverlappingFragment

/**
 * author: Kraos
 * date: 2023/2/24
 * description: 测试可拖拽view
 */
class TestDragViewActivity : BaseActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityTestDragViewBinding>(
            this,
            R.layout.activity_test_drag_view
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_drag_view)
        //可拖动View
//        val dragView = findViewById<ImageView>(R.id.drag_view)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, TestOverlappingFragment()).commit()
    }
}