package com.zzued.campustravel.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzued.campustravel.R;
import com.zzued.campustravel.adapter.HomeMidRcvAdapter;
import com.zzued.campustravel.constant.Constant;
import com.zzued.campustravel.modelclass.Spot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Middle page inside viewpager in {@link com.zzued.campustravel.activity.HomePageActivity}
 */
public class HomeMiddleFragment extends Fragment {
    private HomeMidRcvAdapter adapter;
    private ArrayList<Spot> spots;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    spots.clear();
                    spots.addAll((ArrayList<Spot>)msg.obj);
                    adapter.notifyDataSetChanged();
                    break;
            }
            return true;
        }
    });

    public HomeMiddleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GregorianCalendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View view = inflater.inflate(R.layout.fragment_home_middle, container, false);
        TextView dateView = view.findViewById(R.id.tv_home_mid_date);
        dateView.setText(String.format(Locale.CHINA, "%4d年%2d月%2d日", year, month, day));

        spots = new ArrayList<>();
        adapter = new HomeMidRcvAdapter(getContext(), spots);
        RecyclerView poiRcv = view.findViewById(R.id.rcv_home_mid_poi);
        poiRcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        poiRcv.setAdapter(adapter);

        getDailyNews();

        return view;
    }
    private void getDailyNews(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(Constant.Url_HomeMiddleFragment).build();
                    Response response = client.newCall(request).execute();
                    String res = response.body().string();
                    Message message = new Message();
                    message.what = 1;
                    message.obj = new Gson().fromJson(res, new TypeToken<List<Spot>>(){}.getType());
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
