package com.kraos.querycalendar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * 限制在父布局中的拖动View
 */
public class DragViewLayout extends ConstraintLayout {

    private float moveX;
    private float moveY;

    private float maxTop;
    private float maxBottom;
    private float maxLeft;
    private float maxRight;

    public DragViewLayout(Context context) {
        super(context);
    }

    public DragViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                performClick();
                //得到父视图的right/bottom
                if (maxRight == 0) {
                    ViewGroup parent = (ViewGroup) getParent();
                    maxRight = parent.getWidth() - getWidth();
                    maxBottom = parent.getHeight() - getHeight();
                }

                moveX = getX() - event.getRawX();
                moveY = getY() - event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getRawX() + moveX;
                float y = event.getRawY() + moveY;
                if (x > maxRight) {
                    x = maxRight;
                } else if (x < maxLeft) {
                    x = maxLeft;
                }
                if (y < maxTop) {
                    y = maxTop;
                } else if (y > maxBottom) {
                    y = maxBottom;
                }
                animate()
                        .x(x)
                        .y(y)
                        .setDuration(0)
                        .start();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return true;
    }

}
