package com.kraos.querycalendar.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TagLayout extends ViewGroup {
    List<Rect> childBounds = new ArrayList<Rect>();

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthUsed = 0;
        int heightUsed = 0;
        int lineMaxHeight = 0;
        int lineWidthUsed = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);

        //测量子View的宽高
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            //子View的测量
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            //超出行宽
            if (specMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + child.getMeasuredWidth() > specWidth) {
                //换行
                heightUsed += lineMaxHeight;
                //回车到首位
                lineMaxHeight = 0;
                lineWidthUsed = 0;
                //因为超过当前行宽，换行后重新测量
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }
            //初始化将要给到Layout的Rect
            Rect childBound = null;
            if (childBounds.size() <= i) {
                Rect rect = new Rect();
                childBound = rect;
                childBounds.add(rect);
            } else {
                childBound = childBounds.get(i);
            }
            assert childBound != null;
            childBound.set(lineWidthUsed, heightUsed, lineWidthUsed + child.getMeasuredWidth(), heightUsed + child.getMeasuredHeight());

            //上面已搞定，此处为收尾，每轮循环的数据递增处理
            lineWidthUsed += child.getMeasuredWidth();
            widthUsed = Math.max(widthUsed, lineWidthUsed);
            lineMaxHeight = Math.max(child.getMeasuredHeight(), lineMaxHeight);
        }
        //测量自己的宽高
        int width = widthUsed;
        //由于最后一行的高度未计入，此处加上
        int height = heightUsed + lineMaxHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //遍历子View放置测量好的位置
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final Rect rect = childBounds.get(i);
            getChildAt(i).layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
