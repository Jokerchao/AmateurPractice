package com.kraos.querycalendar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kraos.querycalendar.R;

public class DashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
    }
    public static void bootActivity(Context context) {
        Intent intent = new Intent(context, DashBoardActivity.class);
        context.startActivity(intent);
    }
}