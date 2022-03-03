package com.kraos.querycalendar.view;

import static com.kraos.querycalendar.util.Util.getZForCamera;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.kraos.querycalendar.util.DpUtil;
import com.kraos.querycalendar.util.Util;

public class CameraView extends View {
    public static final float BITMAP_WIDTH = DpUtil.dp2px(200);
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Camera camera = new Camera();

    private float rotate = 10;
    private float topFlip = 0;

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        camera.setLocation(0, 0, getZForCamera());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //上半部分
        canvas.save();
        canvas.translate((BITMAP_WIDTH / 2) + 100, (BITMAP_WIDTH / 2) + 100);
        canvas.rotate(-rotate);
        canvas.clipRect(-BITMAP_WIDTH, -BITMAP_WIDTH, BITMAP_WIDTH, 0);
        canvas.rotate(rotate);
        canvas.translate(-(BITMAP_WIDTH / 2 + 100), -(BITMAP_WIDTH / 2 + 100));
        canvas.drawBitmap(Util.getAvatar(getResources(), (int) BITMAP_WIDTH), 100, 100, paint);
        canvas.restore();

        //下半部分
        canvas.save();
        canvas.translate((BITMAP_WIDTH / 2) + 100, (BITMAP_WIDTH / 2) + 100);
        canvas.rotate(-rotate);
        camera.save();
        camera.rotateX(topFlip);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.clipRect(-BITMAP_WIDTH, 0, BITMAP_WIDTH, BITMAP_WIDTH);
        canvas.rotate(rotate);
        canvas.translate(-(BITMAP_WIDTH / 2 + 100), -(BITMAP_WIDTH / 2 + 100));
        canvas.drawBitmap(Util.getAvatar(getResources(), (int) BITMAP_WIDTH), 100, 100, paint);
        canvas.restore();

    }

    public void setTopFlip(float topFlip) {
        this.topFlip = topFlip;
        invalidate();
    }

    public float getRotate() {
        return rotate;
    }

    public void setRotate(float rotate) {
        this.rotate = rotate;
        invalidate();
    }

    public float getTopFlip() {
        return topFlip;
    }
}
