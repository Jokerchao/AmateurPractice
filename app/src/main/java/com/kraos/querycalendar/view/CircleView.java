package com.kraos.querycalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.kraos.querycalendar.util.DpUtil;

public class CircleView extends View {
    private static final float TOP_OFFSET = DpUtil.dp2px(10);
    private static final float LEFT_OFFSET = DpUtil.dp2px(10);
    public static final float RADIUS = DpUtil.dp2px(80);

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int width = (int) ((TOP_OFFSET + RADIUS) * 2);
        int height = (int) ((LEFT_OFFSET + RADIUS) * 2);

        width = resolveSizeAndState(width, widthMeasureSpec, 0);
        height = resolveSizeAndState(height, widthMeasureSpec, 0);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.RED);
        canvas.drawCircle(TOP_OFFSET + RADIUS,LEFT_OFFSET + RADIUS,RADIUS,paint);
    }
}
