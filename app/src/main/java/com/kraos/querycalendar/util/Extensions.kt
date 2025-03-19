package com.kraos.querycalendar.util

import android.content.res.Resources
import android.util.TypedValue

val Float.px: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )