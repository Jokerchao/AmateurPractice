package com.kraos.querycalendar.view

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.kraos.querycalendar.util.px

/**
 * @author kraos
 * @date 2025/3/24
 * @desc 省份动画View
 */

class ProvinceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        //省份合集
        private val PROVINCE_STR = listOf(
            "北京市",
            "天津市",
            "河北省",
            "山西省",
            "内蒙古自治区",
            "辽宁省",
            "吉林省",
            "黑龙江省",
            "上海市",
            "江苏省",
            "浙江省",
            "安徽省",
            "福建省",
            "江西省",
            "山东省",
            "河南省",
            "湖北省",
            "湖南省",
            "广东省",
            "广西壮族自治区",
            "海南省",
            "重庆市",
            "四川省",
            "贵州省",
            "云南省",
            "西藏自治区",
            "陕西省",
            "甘肃省",
            "青海省",
            "宁夏回族自治区",
            "新疆维吾尔自治区",
            "台湾省",
            "香港特别行政区",
            "澳门特别行政区"
        )
    }

    private val paint = Paint(ANTI_ALIAS_FLAG).apply {
        textSize = 70f.px
        textAlign = Paint.Align.CENTER
    }

    var showText = "北京市"
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(showText, width / 2f, height / 2f, paint)
    }

    class ProvinceEvaluator : TypeEvaluator<String> {
        override fun evaluate(
            fraction: Float,
            startValue: String,
            endValue: String
        ): String? {
            val startIndex = PROVINCE_STR.indexOf(startValue)
            val endIndex = PROVINCE_STR.indexOf(endValue)
            val newIndex = startIndex + ((endIndex - startIndex) * fraction).toInt()
            Log.d(
                "ProvinceEvaluator",
                "fraction:$fraction,startIndex:$startIndex,endIndex:$endIndex,newIndex:$newIndex"
            )
            return PROVINCE_STR[newIndex]
        }

    }

}