package com.kraos.querycalendar.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.kraos.querycalendar.R;

public class DatePickerTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker_test);
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_launcher_background)
                .setTitle("Title")
                .setMessage("Message")
                .setPositiveButton("Button1", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setTitle("点击了对话框上的Button1");
                    }
                }).create().show();
    }

    public static void bootActivity(Context context) {
        Intent intent = new Intent(context, DatePickerTestActivity.class);
        context.startActivity(intent);
    }
}
