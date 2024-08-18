package com.kraos.querycalendar.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    companion object {
        const val TAG = "BaseActivity"

    }

    fun bootActivity(context: Context) {
        val intent = Intent(context, this::class.java)
        context.startActivity(intent)
    }


}
