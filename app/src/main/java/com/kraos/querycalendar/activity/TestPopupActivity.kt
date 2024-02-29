package com.kraos.querycalendar.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.kraos.querycalendar.R
import com.kraos.querycalendar.databinding.ActivityTestPopupBinding

class TestPopupActivity : BaseActivity() {
    private lateinit var binding:ActivityTestPopupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.composeView.setContent {
            TestPopUp()
        }
    }
}