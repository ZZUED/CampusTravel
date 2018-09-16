package com.zzued.campustravel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzued.campustravel.R;
import com.zzued.campustravel.adapter.HomeMidRcvAdapter;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Middle page inside viewpager in {@link com.zzued.campustravel.activity.HomePageActivity}
 */
public class HomeMiddleFragment extends Fragment {

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

        RecyclerView poiRcv = view.findViewById(R.id.rcv_home_mid_poi);
        poiRcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        poiRcv.setAdapter(new HomeMidRcvAdapter());
        return view;
    }
}
