package com.kraos.querycalendar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class SquareImageView extends androidx.appcompat.widget.AppCompatImageView {
    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = getMeasuredHeight();
        int width = getMeasuredWidth();

        int size = Math.max(height, width);
        setMeasuredDimension(size,size);
    }
}
