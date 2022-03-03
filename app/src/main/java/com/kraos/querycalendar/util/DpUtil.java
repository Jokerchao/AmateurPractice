package com.kraos.querycalendar.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class DpUtil {
    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static float dp2px(float dp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
