package com.kraos.querycalendar.activity

import android.os.Bundle
import android.text.Html
import androidx.databinding.DataBindingUtil
import com.kraos.querycalendar.R
import com.kraos.querycalendar.databinding.ActivityTestTextColorFormatBinding

class TestTextColorFormatActivity : BaseActivity() {
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityTestTextColorFormatBinding>(
            this,
            R.layout.activity_test_text_color_format
        )
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_text_color_format)
        val test1 = "第一种颜色"
        val test2 = "第二种颜色"
        val format = String.format(
            applicationContext.getString(R.string.test_color_format),
            "<font color='#4d8ade'>$test1</font>",
            "<font color='#4d11de'>$test2</font>"
        )
        binding.textView.text = (Html.fromHtml(
            format,
            Html.FROM_HTML_MODE_LEGACY
        ))
    }
}