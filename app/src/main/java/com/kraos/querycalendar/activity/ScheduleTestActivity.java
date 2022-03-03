package com.kraos.querycalendar.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kraos.querycalendar.entity.CalendarEvent;
import com.kraos.querycalendar.R;
import com.kraos.querycalendar.util.CalendarUtility;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ScheduleTestActivity extends AppCompatActivity {

    private static final String TAG = "ScheduleTestActivity";

    //日程数据
    private List<CalendarEvent> mEvents = new ArrayList<CalendarEvent>();
    private EventAdapter mAdapter;
    //    private List<ArrayList<String>> data;
    private List<String> data;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_test);
        mEvents.clear();
        initData();
        initListView();
        if (ContextCompat.checkSelfPermission(ScheduleTestActivity.this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ScheduleTestActivity.this, new String[]{
                    Manifest.permission.WRITE_CALENDAR
            }, 1);
        }
//        int accoutnt = CalendarUtility.getAccount(ScheduleTestActivity.this);
//        Log.d(TAG, "onCreate: accoutnt"+accoutnt);
//        CalendarEvent calendarEvent = new CalendarEvent();
//        calendarEvent.setDescriptions("jfodasij");
//        calendarEvent.setNameOfEvent("打排位");
//        CalendarUtility.insert(calendarEvent,ScheduleTestActivity.this);

    }

    private void initListView() {
        RecyclerView recyclerView = findViewById(R.id.rv_calendar_event);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        mAdapter = new EventAdapter();
        if (ContextCompat.checkSelfPermission(ScheduleTestActivity.this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ScheduleTestActivity.this, new String[]{
                    Manifest.permission.READ_CALENDAR
            }, 1);
        }
        Observable.create(new ObservableOnSubscribe<List<CalendarEvent>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CalendarEvent>> e) throws Exception {
                mEvents = CalendarUtility.readCalendarEvent(ScheduleTestActivity.this);
                e.onNext(mEvents);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CalendarEvent>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CalendarEvent> calendarEvents) {
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static void bootActivity(Context context) {
        Intent intent = new Intent(context, ScheduleTestActivity.class);
        context.startActivity(intent);
    }

    class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = View.inflate(parent.getContext(), R.layout.item_calendar, null);
            return new EventViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            EventViewHolder eventViewHolder = (EventViewHolder) holder;
            if (null != mEvents.get(position)) {
                eventViewHolder.tvTitle.setText("主题：" + mEvents.get(position).getNameOfEvent());
                eventViewHolder.tvStartDate.setText("开始时间：" + mEvents.get(position).getStartDates());
                eventViewHolder.tvEndDate.setText("结束时间：" + mEvents.get(position).getEndDates());
                eventViewHolder.tvDescription.setText("描述：" + mEvents.get(position).getDescriptions());
            }
        }

        @Override
        public int getItemCount() {
            return mEvents == null ? 0 : mEvents.size();
        }
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvStartDate;
        TextView tvEndDate;
        TextView tvDescription;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_calendar_title);
            tvStartDate = itemView.findViewById(R.id.item_calendar_start_time);
            tvEndDate = itemView.findViewById(R.id.item_calendar_end_time);
            tvDescription = itemView.findViewById(R.id.item_calendar_description);
        }
    }

}
