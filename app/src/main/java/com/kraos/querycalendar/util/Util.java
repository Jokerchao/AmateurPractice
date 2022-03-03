package com.kraos.querycalendar.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.kraos.querycalendar.R;

public class Util {
    public static Bitmap getAvatar(Resources res, int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, R.drawable.avatar_rengwuxian, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(res, R.drawable.avatar_rengwuxian, options);
    }

    public static float getZForCamera() {
        return - 6 * Resources.getSystem().getDisplayMetrics().density;
    }
}
