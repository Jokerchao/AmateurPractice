package com.kraos.querycalendar.interfaces;

import android.util.Log;

/**
 * 真正的调用对象
 */
public class RealCall implements  ISubject{

    private static final String TAG = "RealCall";

    @Override
    public void request() {
        Log.i(TAG, "request: ");
    }

    @Override
    public void response(String content) {
        Log.i(TAG, "response: " + content);
    }
}
