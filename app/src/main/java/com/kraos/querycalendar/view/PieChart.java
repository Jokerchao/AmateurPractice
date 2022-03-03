package com.kraos.querycalendar.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.kraos.querycalendar.util.DpUtil;

/**
 * 饼图
 */
public class PieChart extends View {
    public static final float RADIUS = DpUtil.dp2px(150);
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF bounds = new RectF();
    private float startAngle = 0;
    private Camera camera = new Camera();

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        camera.rotateX(80);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float midX = getWidth() / 2;
        float midY = getHeight() / 2;
        bounds.set(midX - RADIUS, midY - RADIUS, midX + RADIUS, midY + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int[] colors = {Color.RED, Color.BLACK,Color.GREEN,Color.YELLOW,Color.MAGENTA};
        float[] angles = {60,90,120,60,30};

        canvas.translate(200,100);
        camera.applyToCanvas(canvas);
        canvas.translate(-200,-100);
        canvas.drawRect(0,0,400,200,paint);

/*
        for(int i=0;i<colors.length;i++){
            paint.setColor(colors[i]);
            if(i == 2){
                canvas.save();
                float moveAngle = startAngle + angles[i] / 2;
                float endX = (float) (Math.cos(Math.toRadians(moveAngle)) * DpUtil.dp2px(50));
                float endY = (float) (Math.sin(Math.toRadians(moveAngle)) * DpUtil.dp2px(50));
                canvas.translate(endX,endY);
                canvas.drawArc(bounds,startAngle,angles[i],true,paint);
                canvas.restore();
            }else{
                canvas.drawArc(bounds,startAngle,angles[i],true,paint);
            }
            startAngle += angles[i];
        }
*/
    }

}
