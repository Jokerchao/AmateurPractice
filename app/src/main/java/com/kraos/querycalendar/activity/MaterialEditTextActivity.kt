package com.kraos.querycalendar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kraos.querycalendar.R;
import com.kraos.querycalendar.view.MaterialEditText;

public class MaterialEditTextActivity extends BaseActivity {
    private MaterialEditText metTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia_edit_text);

        metTest = findViewById(R.id.met_test);
        metTest.setEnable(true);
    }

}