package com.kraos.querycalendar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.kraos.querycalendar.R;

import java.util.ArrayList;
import java.util.Arrays;

public class IllegalStateTestActivity extends AppCompatActivity {
    private static final String TAG = "IllegalStateTestActivit";

//    private ArrayList data[] = {"aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd"};
    private ArrayList<String> data = new ArrayList<String>(Arrays.asList("aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd"));
    private ListView listView;
    private ArrayAdapter<String>  adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illegal_state_test);
        listView = (ListView) findViewById(R.id.listview);//在视图中找到ListView
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);//新建并配置ArrayAapeter
//        adapter = new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return 0;
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                return null;
//            }
//        };
        listView.setAdapter(adapter);

        LoadTask mTask = new LoadTask();
        mTask.execute();

    }

    private class LoadTask extends AsyncTask<String, Integer, ArrayList<String>> {
//        ArrayList<String> data = new ArrayList<String>();

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: ");
            ArrayList<String> dataNew = new ArrayList<String>(Arrays.asList("aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd"));
            //                String dataNew[] = {"aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd"};
            for(int i =0 ; i < 20000; i++){
                data.add(String.valueOf(i));
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            Log.d(TAG, "onPostExecute: ");
            adapter.notifyDataSetChanged();
        }
    }

    public static void bootActivity(Context context) {
        Intent intent = new Intent(context, IllegalStateTestActivity.class);
        context.startActivity(intent);
    }
}
