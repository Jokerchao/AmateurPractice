package com.kraos.querycalendar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

public class DragView extends AppCompatButton {

    private float moveX;
    private float moveY;


    public DragView(Context context) {
        super(context);
    }

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
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
                moveX = getX() - event.getRawX();
                moveY = getY() - event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
//                setTranslationX(getX() + (event.getX() - moveX));
//                setTranslationY(getY() + (event.getY() - moveY));
                animate()
                        .x(event.getRawX() + moveX)
                        .y(event.getRawY() + moveY)
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
