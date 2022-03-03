package com.kraos.querycalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.kraos.querycalendar.util.DpUtil;

public class ImageTextView extends View {
    public static final float RADIUS = DpUtil.dp2px(150);

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float midX;
    private float midY;
    private String str = "ababgh";
    private final Rect bounds = new Rect();

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextSize(DpUtil.dp2px(80));
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        midX = getWidth() >> 1;
        midY = getHeight() >> 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画外圈
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(midX, midY, RADIUS, paint);
        paint.setColor(Color.BLUE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(midX - RADIUS, midY - RADIUS, midX + RADIUS, midY + RADIUS,-100,200,false,paint);

        //写中心字
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
//        paint.getTextBounds(str,0,str.length(), bounds);
        float descent = paint.getFontMetrics().descent;
        float ascent = paint.getFontMetrics().ascent;
//        float textY = midY - ((bounds.top + bounds.bottom) >> 1);
        float textY = midY - ((ascent + descent) /2 );
        canvas.drawText(str,midX,textY,paint);

        //居左文字
        paint.setTextSize(DpUtil.dp2px(150));
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(str,0,str.length(), bounds);
        canvas.drawText(str,-bounds.left,paint.getFontSpacing(),paint);
    }
}
