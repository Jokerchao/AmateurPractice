package com.kraos.querycalendar.activity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.kraos.querycalendar.R;
import com.kraos.querycalendar.view.DragView;

import java.util.ArrayList;
import java.util.Arrays;

public class DragViewTestActivity extends AppCompatActivity {
    private static final String TAG = "DragViewTestActivity";

    RelativeLayout rlMain;
    ImageView iv1;
    DragView dv1;
    private float moveX;
    private float moveY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_view_test);
        rlMain = (RelativeLayout) findViewById(R.id.rl_main);//在视图中找到ListView
        dv1 = findViewById(R.id.iv_1);
//        iv1.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                //设置震动反馈
//                iv1.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
//
//                // 创建DragShadowBuilder，我把控件本身传进去
//                View.DragShadowBuilder builder = new View.DragShadowBuilder(iv1);
//                // 剪切板数据，可以在DragEvent.ACTION_DROP方法的时候获取。
//                ClipData data = ClipData.newPlainText("Label", "我是文本内容！");
//                // 开始拖拽
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    iv1.startDragAndDrop(data, builder,iv1, 0);
//                }else{
//                    iv1.startDrag(data, builder, iv1, 0);
//                }
//                return true;
//            }
//        });
//
//        rlMain.setOnDragListener(new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View v, DragEvent event) {
//                //获取事件
//                int action = event.getAction();
//                switch (action) {
//                    case DragEvent.ACTION_DRAG_STARTED:
//                        Log.d("aaa", "开始拖拽");
//                        break;
//                    case DragEvent.ACTION_DRAG_ENDED:
//                        Log.d("aaa", "结束拖拽");
//                        break;
//                    case DragEvent.ACTION_DRAG_ENTERED:
//                        Log.d("aaa", "拖拽的view进入监听的view时");
//                        break;
//                    case DragEvent.ACTION_DRAG_EXITED:
//                        Log.d("aaa", "拖拽的view离开监听的view时");
//                        rlMain.setBackgroundColor(Color.parseColor("#303F9F"));
//                        break;
//                    case DragEvent.ACTION_DRAG_LOCATION:
////                        float x = event.getX();
////                        float y = event.getY();
//                        rlMain.setBackgroundColor(Color.GRAY);
////                        Log.i("aaa", "拖拽的view在监听view中的位置:x =" + x + ",y=" + y);
//                        float x = event.getX();
//                        float y = event.getY();
//
//                        if(event != null && event.getLocalState() != null){
//                            View localState = (View) event.getLocalState();
//                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                            layoutParams.leftMargin = (int) x - localState.getWidth()/2;
//                            layoutParams.topMargin = (int) y - localState.getHeight()/2;
//                            ((ViewGroup) localState.getParent()).removeView(localState);
//                            rlMain.addView(localState, layoutParams);
//                        }
//                        break;
//                    case DragEvent.ACTION_DROP:
//                        Log.i("aaa", "释放拖拽的view");
//
//                        break;
//                }
//                return true;
//            }
//        });

    }

    public static void bootActivity(Context context) {
        Intent intent = new Intent(context, DragViewTestActivity.class);
        context.startActivity(intent);
    }
}
