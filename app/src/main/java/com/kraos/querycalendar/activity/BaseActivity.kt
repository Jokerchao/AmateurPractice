package com.kraos.querycalendar.activity;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public void bootActivity(Context context) {
        Intent intent = new Intent(context, this.getClass());
        context.startActivity(intent);
    }
}
