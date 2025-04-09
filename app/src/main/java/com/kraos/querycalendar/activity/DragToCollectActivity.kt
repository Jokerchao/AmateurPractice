package com.kraos.querycalendar.activity

import android.os.Bundle
import com.kraos.querycalendar.databinding.ActivityDragHelperBinding

/**
 * @author kraos
 * @date 2025/4/9
 */
class DragToCollectActivity : BaseActivity() {
    private lateinit var binding: ActivityDragHelperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDragHelperBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}