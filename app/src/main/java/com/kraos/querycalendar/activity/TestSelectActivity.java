package com.kraos.querycalendar.activity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kraos.querycalendar.R;
import com.kraos.querycalendar.SelectableHelper.OnSelectListener;
import com.kraos.querycalendar.SelectableHelper.SelectableTextHelper;

public class TestSelectActivity extends AppCompatActivity {



    private static final String TAG = "TestSelectActivity";

    TextView tvSelection;
    EditText etSelection;
    EditText etSelection2;

    //SelectableTextHelper相关工具类
    private SelectableTextHelper mSelectableTextHelper;
    private LinearLayout llRoot;

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        Log.d(TAG, "startActivityForResult: canStart"+canStart(intent));
        Log.d(TAG, "startActivityForResult: intent"+intent.getAction());
        if (!canStart(intent)) {
            return;
        } else {
            super.startActivityForResult(intent, requestCode);
        }
    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        Log.d(TAG, "startActivityForResult: canStart"+canStart(intent));
        if (!canStart(intent)) {
            return;
        } else {
            super.startActivityForResult(intent, requestCode, options);
        }
    }

    private boolean canStart(Intent intent) {
        if (intent.getAction() == Intent.ACTION_CHOOSER || intent.getAction() == Intent.ACTION_VIEW || intent.getAction() == Intent.ACTION_SEARCH) {
            return false;
        } else {
            return true;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_select);
        tvSelection = findViewById(R.id.tv_selected);
        etSelection = findViewById(R.id.et_selected);
        etSelection2 = findViewById(R.id.et_selected2);

        etSelection.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.selection_action_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//                MenuInflater menuInflater = mode.getMenuInflater();
//                menu.clear();
//                menuInflater.inflate(R.menu.selection_action_menu, menu);
                return false;

            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                //根据item的ID处理点击事件
                switch (item.getItemId()) {
                    case R.id.Informal22:
                        Toast.makeText(TestSelectActivity.this, "点击的是自定义", Toast.LENGTH_SHORT).show();
                        mode.finish();//收起操作菜单
                        break;
                    case R.id.Informal33:
                        Toast.makeText(TestSelectActivity.this, "点击的是创建待办", Toast.LENGTH_SHORT).show();
                        mode.finish();
                        break;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        tvSelection.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.selection_action_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menu.clear();
                menuInflater.inflate(R.menu.selection_action_menu, menu);
                return true;

            }


            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                //根据item的ID处理点击事件
                switch (item.getItemId()) {
                    case R.id.Informal22:
                        Toast.makeText(TestSelectActivity.this, "点击的是自定义", Toast.LENGTH_SHORT).show();
                        mode.finish();//收起操作菜单
                        break;
                    case R.id.Informal33:
                        Toast.makeText(TestSelectActivity.this, "点击的是创建待办", Toast.LENGTH_SHORT).show();
                        mode.finish();
                        break;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

//        etSelection2.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_CANCEL:
//                    case MotionEvent.ACTION_UP:
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                setSelectHandleDisabled();
//                            }
//                        }, 500); // 延迟50ms，等room显示handle完后，再隐藏
//                        break;
//                    default:
//                        break;
//                }
//                return TestSelectActivity.super.onTouchEvent(event);
//            }
//        });
        etSelection2.setTextIsSelectable(false);
        mSelectableTextHelper = new SelectableTextHelper.Builder(etSelection2)
                .setSelectedColor(getResources().getColor(R.color.selected_blue))
                .setCursorHandleSizeInDp(20)
                .setCursorHandleColor(getResources().getColor(R.color.cursor_handle_color))
                .build();
        mSelectableTextHelper = new SelectableTextHelper.Builder(tvSelection)
                .setSelectedColor(getResources().getColor(R.color.selected_blue))
                .setCursorHandleSizeInDp(20)
                .setCursorHandleColor(getResources().getColor(R.color.cursor_handle_color))
                .build();
        mSelectableTextHelper.setSelectListener(new OnSelectListener() {
            @Override
            public void onTextSelected(CharSequence content) {

            }
        });



    }

    private void setSelectHandleDisabled() {
        try {
            Field mEditor = TextView.class.getDeclaredField("mEditor");
            mEditor.setAccessible(true);
            Object object = mEditor.get(this);
            Class<?> mClass = object.getClass();
            // 选中时handle
            Method selectionController = mClass.getDeclaredMethod("getSelectionController");
            selectionController.setAccessible(true);
            Object invokeSelect = selectionController.invoke(object);
            Method hideSelect = invokeSelect.getClass().getDeclaredMethod("hide");
            hideSelect.invoke(invokeSelect);
            // 插入时handle
            Method insertionController = mClass.getDeclaredMethod("getInsertionController");
            insertionController.setAccessible(true);
            Object invokeInsert = insertionController.invoke(object);
            Method hideInsert = invokeInsert.getClass().getDeclaredMethod("hide");
            hideInsert.invoke(invokeInsert);
        } catch (Exception e) {

        }
    }

    public static void bootActivity(Context context){
        Intent intent = new Intent(context, TestSelectActivity.class);
        context.startActivity(intent);
    }

}