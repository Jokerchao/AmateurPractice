package com.kraos.querycalendar;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Description:
 * Author:Kraos dengbch@crop.21cn.com
 * Date:2019/11/15 10:02
 */
public class CalendarUtility {
    private static final String TAG = "CalendarUtility";
    public static List<CalendarEvent> calendar = new ArrayList<CalendarEvent>();

    public static List<CalendarEvent> readCalendarEvent(Context context) {
        Cursor cursor = context.getContentResolver()
                .query(CalendarContract.Events.CONTENT_URI, null, null,
                        null, null);
        cursor.moveToFirst();
        calendar.clear();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                CalendarEvent event = new CalendarEvent();
                event.setNameOfEvent(cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE)));
                event.setStartDates(getDate(Long.parseLong(cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DTSTART)))));
                event.setEndDates(getDate(Long.parseLong(cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DTEND)))));
                event.setDescriptions(cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION)));
                Log.d(TAG, "readCalendarEvent:  ORGANIZER:" + cursor.getString(cursor.getColumnIndex(CalendarContract.Events.ORGANIZER)));
                Log.d(TAG, "readCalendarEvent: CALENDAR_ID" + cursor.getString(cursor.getColumnIndex(CalendarContract.Events.CALENDAR_ID)));
                Log.d(TAG, "readCalendarEvent: _ID" + cursor.getString(cursor.getColumnIndex(CalendarContract.Events._ID)));

                calendar.add(event);
            }
        }
        return calendar;
    }

    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

//    private int getSystemCalendarAccount(Context context){
//        int account = -1;
//        Cursor userCursor =  null;
//        try {
//            userCursor = mContext.getContentResolver().query(Uri.parse(CALANDER_URL), null, null, null, null);
//            if (userCursor != null && userCursor.getCount() > 0){
//                userCursor.moveToFirst();
//                account = userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID));
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            if (userCursor != null) {
//                userCursor.close();
//            }
//        }
//        return account;
//    }

    private static long setFoowwCalendar(Context context) {
        ContentValues contentValues = new ContentValues();
        //  ????????????
        contentValues.put(CalendarContract.Calendars.NAME, "KraosCalendar");
        //  ??????????????????????????????
        contentValues.put(CalendarContract.Calendars.ACCOUNT_NAME, "KraosCalendar");
        //  ???????????????com.android.exchange
        contentValues.put(CalendarContract.Calendars.ACCOUNT_TYPE, "com.android.exchange");
        //  ??????????????????????????????
        contentValues.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "Kraos_Calendar");
        //  ????????????????????????????????????????????????????????????
        //  0????????????????????????????????????????????????????????????
        //  ???1???????????????????????????????????????????????????????????????
        //  ??????????????????CalendarContract.instances?????????????????????
        contentValues.put(CalendarContract.Calendars.VISIBLE, 1);
        //  ??????????????????
        contentValues.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE);
        //  ????????????
        contentValues.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        //  ??????????????????????????????????????????????????????????????????????????????????????????????????????
        //  0?????????????????????????????????????????????????????????????????????????????????
        //  1?????????????????????????????????????????????????????????????????????????????????
        contentValues.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        //  ??????
//        contentValues.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, TimeZone.getDefault().getID());
        //  ???????????????
        contentValues.put(CalendarContract.Calendars.OWNER_ACCOUNT, "Kraos_Calendar");
        contentValues.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);

        Uri calendarUri = CalendarContract.Calendars.CONTENT_URI;
        calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, "KraosCalendar")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, "com.android.exchange")
                .build();

        Uri result = context.getContentResolver().insert(calendarUri, contentValues);
        return result == null ? -1 : ContentUris.parseId(result);
    }

    public static int getAccount(Context context) {
        return (int) setFoowwCalendar(context);
    }

    public static void insert(CalendarEvent calendarEvent,Context context) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, calendarEvent.getNameOfEvent())
                .putExtra(CalendarContract.Events.DESCRIPTION, calendarEvent.getDescriptions())
//                .putExtra(CalendarContract.Events.EVENT_LOCATION, calendarEvent.getLocation())
                .putExtra(CalendarContract.Events.DTSTART, calendarEvent.getStartDates())
                .putExtra(CalendarContract.Events.DTEND, calendarEvent.getEndDates())
                .putExtra(CalendarContract.Reminders.MINUTES, 0);
        context.startActivity(intent);
    }
}
