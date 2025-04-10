package com.kraos.querycalendar.activity

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.kraos.querycalendar.R
import com.kraos.querycalendar.databinding.ActivityTransConstrintLayoutBinding

class TransConstraintLayoutActivity : BaseActivity() {

    private lateinit var binding: ActivityTransConstrintLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransConstrintLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.main.setOnClickListener { view ->

            TransitionManager.beginDelayedTransition(view as ConstraintLayout)

            ConstraintSet().apply {
                clone(
                    this@TransConstraintLayoutActivity,
                    R.layout.activity_trans_constrint_layout_change
                )
                applyTo(view)
            }
        }

    }
}