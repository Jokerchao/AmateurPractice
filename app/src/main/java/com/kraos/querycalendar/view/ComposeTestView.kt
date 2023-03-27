package com.kraos.querycalendar.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.kraos.querycalendar.R

/**
 * @author kraos
 * @date 2023/3/24
 */
class ComposeTestView {


}

@Composable
@Preview
fun TestLearn(name: String = "Hello World!") {

    var gameName by remember { mutableStateOf(true) }

    Column(
        Modifier
            .background(Color.Gray)
            .fillMaxSize()
    ) {
        val textWord = remember(gameName) {
            if (gameName) {
                "明日方舟"
            } else {
                "鬼泣5"
            }
        }
        Text(textWord)
        Image(painter = painterResource(id = R.drawable.test), contentDescription = "image")
        Button(onClick = { gameName = !gameName }) {
            Text("修改")
        }
    }
}