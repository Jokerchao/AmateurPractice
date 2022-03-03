package com.kraos.querycalendar.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kraos.querycalendar.R;
import com.kraos.querycalendar.util.DpUtil;

public class MaterialEditText extends androidx.appcompat.widget.AppCompatEditText {
    private static final String TAG = "MaterialEditText";

    public static final float PADDING_TOP = DpUtil.dp2px(22);

    private static final int TEXT_VERTICAL_OFFSET = (int) DpUtil.dp2px(22);
    private static final int TEXT_HORIZONTAL_OFFSET = (int) DpUtil.dp2px(5);
    private static final int TEXT_SIZE = (int) DpUtil.dp2px(15);
    private static final int TEXT_ANIMATION_OFFSET = (int) DpUtil.dp2px(16);


    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private boolean isLabelShow ;

    private float labelFraction = 0;
    private ObjectAnimator animator;

    private boolean enable = false;

    public MaterialEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
        enable = typedArray.getBoolean(R.styleable.MaterialEditText_enable, true);
        typedArray.recycle();
        setBound();
        paint.setTextSize(TEXT_SIZE);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (enable) {
                    if (!isLabelShow && !TextUtils.isEmpty(s)) {
                        isLabelShow = true;
                        getAnimator().start();
                    } else if (isLabelShow && TextUtils.isEmpty(s)) {
                        isLabelShow = false;
                        getAnimator().reverse();
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setBound() {
        Rect bounds = getBackground().getBounds();
        if (enable) {
            setPadding(getPaddingLeft(), (int) (bounds.top + PADDING_TOP), getPaddingRight(), getPaddingBottom());
        } else {
            setPadding(getPaddingLeft(), bounds.top, getPaddingRight(), getPaddingBottom());

        }
    }

    private ObjectAnimator getAnimator() {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(MaterialEditText.this, "labelFraction", 0, 1);
        }
        return animator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: ");

        paint.setAlpha((int) (0xff * labelFraction));
        int offset = (int) (TEXT_ANIMATION_OFFSET * ((1 - labelFraction)));
        canvas.drawText(getHint().toString(), TEXT_HORIZONTAL_OFFSET, TEXT_VERTICAL_OFFSET + offset, paint);
    }

    public float getLabelFraction() {
        return labelFraction;
    }

    public void setLabelFraction(float labelFraction) {
        this.labelFraction = labelFraction;
        invalidate();
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        setBound();
    }
}
