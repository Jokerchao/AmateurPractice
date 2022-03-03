package com.kraos.querycalendar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kraos.querycalendar.R;

public class PieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
    }

    public static void bootActivity(Context context) {
        Intent intent = new Intent(context, PieChartActivity.class);
        context.startActivity(intent);
    }
}