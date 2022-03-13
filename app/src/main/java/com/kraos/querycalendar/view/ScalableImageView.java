package com.kraos.querycalendar.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

import com.kraos.querycalendar.R;
import com.kraos.querycalendar.util.DpUtil;
import com.kraos.querycalendar.util.Util;

public class ScalableImageView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {
    private static final String TAG = "ScalableImageView";
    private static final float BITMAP_SIZE = DpUtil.dp2px(370);
    private static final float OVERS_CALE = 1.5F;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Bitmap bitmap;
    private float midX;
    private float midY;

    private float bitmapHeight;
    private float bitmapWidth;
    private float bigScale;
    private float smallScale;

    private final GestureDetector gestureDetector;
    private boolean isBig;
    private ObjectAnimator objectAnimator;
    private float fraction;

    private float offsetX;
    private float offsetY;
    private float originalOffsetX;
    private float originalOffsetY;
    private float currentScale;

    private final OverScroller overScroller;

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bitmap = Util.getAvatar(getResources(), (int) BITMAP_SIZE);
        gestureDetector = new GestureDetector(context, this);
        gestureDetector.setOnDoubleTapListener(this);
        overScroller = new OverScroller(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        midX = getWidth() >> 1;
        midY = getHeight() >> 1;
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();

        originalOffsetX = midX - (bitmapWidth / 2);
        originalOffsetY = midY - (bitmapHeight / 2);

        if (bitmapWidth / bitmapHeight > midX / midY) {
            smallScale = getWidth() / bitmapWidth;
            bigScale = getHeight() / bitmapHeight * OVERS_CALE;
        } else {
            smallScale = getHeight() / bitmapHeight;
            bigScale = getWidth() / bitmapWidth * OVERS_CALE;
        }
        currentScale = smallScale;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(offsetX, offsetY);
        canvas.scale(currentScale, currentScale, getWidth() >> 1, getHeight() >> 1);
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (isBig) {
            offsetX -= distanceX;
            offsetX = Math.min(offsetX, (bigScale * bitmapWidth / 2 - midX));
            offsetX = Math.max(offsetX, -(bigScale * bitmapWidth / 2 - midX));
            offsetY -= distanceY;
            offsetY = Math.min(offsetY, (bigScale * bitmapHeight / 2 - midY));
            offsetY = Math.max(offsetY, -(bigScale * bitmapHeight / 2 - midY));
            invalidate();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (isBig) {
            overScroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                    (int) -(bigScale * bitmapWidth / 2 - midX),
                    (int) (bigScale * bitmapWidth / 2 - midX),
                    (int) (-(bigScale * bitmapHeight / 2 - midY)),
                    (int) (bigScale * bitmapHeight / 2 - midY));
            postOnAnimation(this);
        }
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        isBig = !isBig;
        if (isBig) {
            offsetX = (e.getX()-midX) - (e.getX() - midX) * bigScale / smallScale;
            offsetY = (e.getY()-midY) - (e.getY() - midY) * bigScale / smallScale;
            getAnimator().start();
        } else {
            getAnimator().reverse();
        }
        Log.i(TAG, "onDoubleTap: isBig->" + isBig);
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    private ObjectAnimator getAnimator() {
        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0);
            objectAnimator.setFloatValues(smallScale, bigScale);
            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if(!isBig){
                        offsetX = 0;
                        offsetY = 0;
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        return objectAnimator;
    }

    private float getFraction() {
        return fraction;
    }

    private void setFraction(float fraction) {
        this.fraction = fraction;
        invalidate();
    }

    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    @Override
    public void run() {
        if (overScroller.computeScrollOffset()) {
            offsetX = overScroller.getCurrX();
            offsetY = overScroller.getCurrY();
            invalidate();
            postOnAnimation(this);
        }
    }
}
