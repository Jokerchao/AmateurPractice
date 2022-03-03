package com.kraos.querycalendar.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.kraos.querycalendar.entity.CalendarEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Description: 获取本地系统日程的工具类
 * Author:Kraos dengbch@crop.21cn.com
 * Date:2019/11/15 10:02
 */
public class CalendarUtility {
    private static final String TAG = "CalendarUtility";
    public static List<CalendarEvent> calendar = new ArrayList<CalendarEvent>();

    public static List<CalendarEvent> readCalendarEvent(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{
                    Manifest.permission.WRITE_CALENDAR
            }, 1);
        }
        Uri uri = CalendarContract.Events.CONTENT_URI;
        Cursor cursor = context.getContentResolver()
                .query(uri, null, null,
                        null, null);
        cursor.moveToFirst();
        calendar.clear();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                CalendarEvent event = new CalendarEvent();
                event.setNameOfEvent(cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE)));
                event.setStartDates(getDate(cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART))));
                event.setEndDates(getDate(cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTEND))));
                event.setDescriptions(cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION)));
                Log.d(TAG, "readCalendarEvent: CALENDAR_ID:" + cursor.getString(cursor.getColumnIndex(CalendarContract.Events.CALENDAR_ID)));  //事件所属的日历的_ID
                Log.d(TAG, "readCalendarEvent:  ORGANIZER:" + cursor.getString(cursor.getColumnIndex(CalendarContract.Events.ORGANIZER)));  //事件的组织者（所有者）的电子邮件
                Log.d(TAG, "readCalendarEvent: TITLE:" + cursor.getString(cursor.getColumnIndex(CalendarContract.Events.UID_2445))); //事件的标题
                Log.d(TAG, "readCalendarEvent: _ID:" + cursor.getString(cursor.getColumnIndex(CalendarContract.Events._ID)));
                calendar.add(event);
            }
        }
        return calendar;
    }

    /**
     * 返回String类型的日程
     *
     * @param milliSeconds
     * @return
     */
    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

}
