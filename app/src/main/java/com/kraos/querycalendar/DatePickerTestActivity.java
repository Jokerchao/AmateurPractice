package com.kraos.querycalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class DatePickerTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker_test);
    }

    public static void bootActivity(Context context) {
        Intent intent = new Intent(context, DatePickerTestActivity.class);
        context.startActivity(intent);
    }
}
