package com.kraos.querycalendar.util

import android.content.res.Resources
import android.util.TypedValue

/**
 * @author kraos
 * @date 2025/3/20
 */
val Float.px: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )