package com.kraos.querycalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.kraos.querycalendar.util.DpUtil;

public class TestCustomView extends View {
    //反锯齿一般都要加
    final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    final float[] points = new float[]{0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
    final Path path = new Path(); // 初始化 Path 对象



    public TestCustomView(Context context) {
        super(context);
    }

    public TestCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //染成纯黑色，覆盖掉原有内容
        canvas.drawColor(Color.BLACK);
        //在原有的绘制效果上加一层半透明的红色遮罩
        canvas.drawColor(Color.parseColor("#88880000"));
        // 绘制一个圆
        canvas.drawCircle(300, 300, 200, paint);

        //绘制矩形
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(100, 100, 500, 500, paint);

        //绘制矩形
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(700, 100, 1100, 500, paint);

        paint.setColor(Color.parseColor("#009688"));
        canvas.drawRect(30, 30, 230, 180, paint);

        paint.setStrokeWidth(20);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.parseColor("#FF9800"));
        canvas.drawLine(300, 30, 450, 180, paint);

        paint.setColor(Color.parseColor("#E91E63"));
        canvas.drawText("HenCoder", 500, 130, paint);

        // 绘制四个点：(50, 50) (50, 100) (100, 50) (100, 100)
        canvas.drawPoints(points, 2 /* 跳过两个数，即前两个 0 */,
                8 /* 一共绘制 8 个数（4 个点）*/, paint);

        //画椭圆
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawOval(400, 50, 700, 200, paint);

        //圆角矩形
        canvas.drawRoundRect(100, 100, 500, 300, 50, 50, paint);

        //弧形或扇形
        paint.setStyle(Paint.Style.FILL); // 填充模式
        canvas.drawArc(200, 100, 800, 500, -110, 100, true, paint); // 绘制扇形
        canvas.drawArc(200, 100, 800, 500, 20, 140, false, paint); // 绘制弧形
        paint.setStyle(Paint.Style.STROKE); // 画线模式
        canvas.drawArc(200, 100, 800, 500, 180, 60, false, paint); // 绘制不封口的弧形

        canvas.drawColor(Color.WHITE);
        //画自定义图形
        // 使用 path 对图形进行描述（这段描述代码不必看懂）
        path.addArc(200, 200, 400, 400, -225, 225);
        path.arcTo(400, 200, 600, 400, -180, 225, false);
        path.lineTo(400, 542);
        //close() 和 lineTo(起点坐标) 是完全等价的
        path.close();
//        path.moveTo(200, 100); // 我移~~
//        path.lineTo(100, 100); // 由当前位置 (0, 0) 向 (100, 100) 画一条直线
//        path.rLineTo(100, 60); // 由当前位置 (100, 100) 向正右方 100 像素的位置画一条直线
        canvas.drawPath(path, paint); // 绘制出 path 描述的图形（心形），大功告成

        paint.setStyle(Paint.Style.FILL);
        /* ------ shader（着色器） -----**/
        //线性渐变
/*
        Shader shader = new LinearGradient(100, 100, 500, 500, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
*/
        //RadialGradient 辐射渐变
        /*Shader shader = new RadialGradient(300, 300, 200, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);*/

        //SweepGradient 扫描渐变
        Shader shader = new SweepGradient(300, 300, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"));
        paint.setShader(shader);

        canvas.drawCircle(DpUtil.dp2px(300), DpUtil.dp2px(300), DpUtil.dp2px(200), paint);
    }
}
