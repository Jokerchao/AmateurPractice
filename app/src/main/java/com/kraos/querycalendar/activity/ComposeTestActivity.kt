package com.kraos.querycalendar.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import com.kraos.querycalendar.R

class ComposeTestActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val text = remember { mutableStateOf("Hello World!") }
            Text(text = "Hello World!")
            Image(painter = painterResource(id = R.drawable.test), contentDescription = "image")
        }
    }
}