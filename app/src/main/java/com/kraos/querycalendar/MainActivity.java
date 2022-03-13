package com.kraos.querycalendar;

import android.app.Dialog;
import android.app.NotificationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kraos.querycalendar.activity.CameraViewActivity;
import com.kraos.querycalendar.activity.CoroutinesTestActivity;
import com.kraos.querycalendar.activity.DashBoardActivity;
import com.kraos.querycalendar.activity.DatePickerTestActivity;
import com.kraos.querycalendar.activity.DragViewTestActivity;
import com.kraos.querycalendar.activity.IllegalStateTestActivity;
import com.kraos.querycalendar.activity.ImageTextActivity;
import com.kraos.querycalendar.activity.KotlinTestActivity;
import com.kraos.querycalendar.activity.MaterialEditTextActivity;
import com.kraos.querycalendar.activity.PieChartActivity;
import com.kraos.querycalendar.activity.RichEditorTestActivity;
import com.kraos.querycalendar.activity.ScalableImageViewActivity;
import com.kraos.querycalendar.activity.ScheduleTestActivity;
import com.kraos.querycalendar.activity.TagLayoutActivity;
import com.kraos.querycalendar.activity.TestCustomViewActivity;
import com.kraos.querycalendar.activity.TestProxyActivity;
import com.kraos.querycalendar.activity.TestSelectActivity;
import com.kraos.querycalendar.view.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //数据源
    private ArrayList mList = new ArrayList() {{
        add("文字选择测试");
        add("日程导入测试");
        add("时间选择器测试");
        add("富文本编辑器测试");
        add("协程测试");
        add("ListView异步Notify测试");
        add("拖拽view测试");
        add("Kotlin语法测试");
        add("自定义view测试");
        add("仪表盘自定义View");
        add("饼图自定义View");
        add("自定义TextView");
        add("自定义CameraView");
        add("MaterialEditText练习");
        add("TagLayout练习");
        add("ScalableImageView练习");
        add("动态代理练习");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        Log.d(TAG, "onCreate: " + NotificationManagerCompat.from(getApplicationContext()).areNotificationsEnabled());
        initListView();
    }

    private void initListView() {
        RecyclerView rv = findViewById(R.id.rv_test);
        rv.setLayoutManager(new LinearLayoutManager(this));
        RvAdapter adapter = new RvAdapter(mList);
        rv.setAdapter(adapter);
    }

    class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        //数据源
        private ArrayList mList;

        public RvAdapter(List<String> list) {
            mList = (ArrayList) list;
            String string = list.get(0);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View item = View.inflate(parent.getContext(), R.layout.item_rv, null);
            return new MyViewHolder(item);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            String string = (String) mList.get(position);
            viewHolder.tv.setText(string);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 0:
                            TestSelectActivity.bootActivity(MainActivity.this);
                            break;
                        case 1:
                            ScheduleTestActivity.bootActivity(MainActivity.this);
                            break;
                        case 2:
                            DatePickerTestActivity.bootActivity(MainActivity.this);
                            break;
                        case 3:
                            RichEditorTestActivity.bootActivity(MainActivity.this);
                            break;
                        case 4:
                            CoroutinesTestActivity.Companion.bootActivity(MainActivity.this);
                            break;
                        case 5:
                            IllegalStateTestActivity.bootActivity(MainActivity.this);
                            break;
                        case 6:
                            DragViewTestActivity.bootActivity(MainActivity.this);
                            break;
                        case 7:
                            KotlinTestActivity.Companion.bootActivity(MainActivity.this);
                            break;
                        case 8:
                            TestCustomViewActivity.bootActivity(MainActivity.this);
                            break;
                        case 9:
                            DashBoardActivity.bootActivity(MainActivity.this);
                            break;
                        case 10:
                            PieChartActivity.bootActivity(MainActivity.this);
                            break;
                        case 11:
                            new ImageTextActivity().bootActivity(MainActivity.this);
                            break;
                        case 12:
                            new CameraViewActivity().bootActivity(MainActivity.this);
                            break;
                        case 13:
                            new MaterialEditTextActivity().bootActivity(MainActivity.this);
                            break;
                        case 14:
                            new TagLayoutActivity().bootActivity(MainActivity.this);
                            break;
                        case 15:
                            new ScalableImageViewActivity().bootActivity(MainActivity.this);
                            break;
                        case 16:
                            new TestProxyActivity().bootActivity(MainActivity.this);
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.item_text);
        }
    }
}
