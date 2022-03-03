package com.kraos.querycalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.kraos.querycalendar.util.DpUtil;

public class DashboardView extends View {
    private static final int ANGLE = 120;
    private static final int RADIUS = 150;
    private static final int LENGTH = 100;
    private static final float DASH_STEP = 20;

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final PathEffect dashEffect;
    private final Path dashPath = new Path();
    private final Path arcPath = new Path();
    private final float pxRadius;
    private final int startAngle;
    private final int swipeAngle;

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        //代码块的代码在每次构造函数之后都会执行,可用于初始化
        pxRadius = DpUtil.dp2px(RADIUS);
        startAngle = 90 + ANGLE / 2;
        swipeAngle = 360 - ANGLE;
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        float midX = getWidth() >> 1;
        float midY = getHeight() >> 1;
        arcPath.addArc(midX - pxRadius, midY - pxRadius, midX + pxRadius, midY + pxRadius, startAngle, swipeAngle);
        PathMeasure pathMeasure = new PathMeasure(arcPath, false);
        float advance = (pathMeasure.getLength() - DpUtil.dp2px(2)) / DASH_STEP;
        dashPath.addRect(0, 0, DpUtil.dp2px(2), DpUtil.dp2px(10), Path.Direction.CW);
        dashEffect = new PathDashPathEffect(dashPath, advance, 0, PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float midX = getWidth() >> 1;
        float midY = getHeight() >> 1;
        //画弧
        canvas.drawArc(midX - pxRadius, midY - pxRadius, midX + pxRadius, midY + pxRadius, startAngle, swipeAngle, false, paint);

        //画刻度
        paint.setPathEffect(dashEffect);
        canvas.drawArc(midX - pxRadius, midY - pxRadius, midX + pxRadius, midY + pxRadius, startAngle, swipeAngle, false, paint);
        paint.setPathEffect(null);

        //画指针
        int curAngle = 90;
        float endX = (float) (Math.cos(Math.toRadians(getAngleFromMark(5))) * DpUtil.dp2px(LENGTH) + midX);
        float endY = (float) (Math.sin(Math.toRadians(getAngleFromMark(5))) * DpUtil.dp2px(LENGTH) + midY);
        canvas.drawLine(midX, midY, endX, endY, paint);
    }

    public int getAngleFromMark(int mark) {
        return 90 + ANGLE / 2 + ((360 - ANGLE) / 20 * mark);
    }
}
