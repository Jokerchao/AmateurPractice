package com.kraos.querycalendar.activity

import android.os.Bundle
import androidx.core.view.postDelayed
import com.kraos.querycalendar.R
import com.kraos.querycalendar.view.MaterialEditText
import com.kraos.querycalendar.view.MaterialProEditText

class MaterialEditTextActivity : BaseActivity() {
    private val metTest: MaterialEditText? = null

    private lateinit var metProTest: MaterialProEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materia_edit_text)

        //        metTest = findViewById(R.id.met_test);
//        metTest.setEnable(true);
        metProTest = findViewById<MaterialProEditText>(R.id.met_pro_test)
        metProTest.postDelayed(3000L) { metProTest.showLabel = false }
    }
}