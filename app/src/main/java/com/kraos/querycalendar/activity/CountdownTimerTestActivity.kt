package com.kraos.querycalendar.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kraos.querycalendar.compose_widget.CountdownTimerWidget

/**
 * @author kraos
 * @date 2024/11/19
 * @desc 倒计时动画测试
 */
class CountdownTimerTestActivity :BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountdownTimerWidget.Content(
                modifier = Modifier.size(150.dp),
            )
        }
    }

}