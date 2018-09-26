package com.zzued.campustravel.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzued.campustravel.R;
import com.zzued.campustravel.activity.FlatMapActivity;
import com.zzued.campustravel.activity.RouteRecommendActivity;
import com.zzued.campustravel.activity.ScenicAreaIntroActivity;
import com.zzued.campustravel.activity.SearchActivity;
import com.zzued.campustravel.activity.ThermalMapActivity;
import com.zzued.campustravel.activity.VoiceAssistActivity;
import com.zzued.campustravel.activity.VoiceIntroActivity;
import com.zzued.campustravel.adapter.SpotListAdapter;
import com.zzued.campustravel.constant.Constant;
import com.zzued.campustravel.modelclass.Spot;
import com.zzued.campustravel.view.CustomHomeLeftGridItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Left page inside viewpager in {@link com.zzued.campustravel.activity.HomePageActivity}
 */
public class HomeLeftFragment extends Fragment {

    private SpotListAdapter spotlistadapter;
    private List<Spot> spotList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_left, container, false);
        initListener(view);

        spotlistadapter = new SpotListAdapter(getContext(), spotList);
        RecyclerView rcvSpotList = view.findViewById(R.id.rcv_home_left_spot);
        rcvSpotList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        rcvSpotList.setAdapter(spotlistadapter);
        return view;
    }

    public void setData(List<Spot> spotList){
        this.spotList = spotList;
        spotlistadapter.notifyDataSetChanged();

    }

    /**
     * 初始化
     *
     * @param view 布局 view
     */
    private void initListener(View view) {
        // another search
        TextView tvSearch = view.findViewById(R.id.tv_home_left_nearby_search);
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
                getActivity().overridePendingTransition(Animation.ABSOLUTE, android.R.anim.fade_out);
            }
        });
        // search listener
        LinearLayout llSearchHolder = view.findViewById(R.id.ll_home_left_search_holder);
        llSearchHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
                getActivity().overridePendingTransition(Animation.ABSOLUTE, android.R.anim.fade_out);
            }
        });

        // map type
        CustomHomeLeftGridItem mapItem = view.findViewById(R.id.gi_home_left_map);
        mapItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireDialog(new String[]{getString(R.string.see_flat_map), getString(R.string.see_thermal_map)},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0)
                                    startActivity(new Intent(getContext(), FlatMapActivity.class));
                                else
                                    startActivity(new Intent(getContext(), ThermalMapActivity.class));

                            }
                        });
            }
        });

        // route recommend
        CustomHomeLeftGridItem routeItem = view.findViewById(R.id.gi_home_left_route);
        routeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RouteRecommendActivity.class));
            }
        });

        // voice introduction
        CustomHomeLeftGridItem voiceItem = view.findViewById(R.id.gi_home_left_voice);
        voiceItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireDialog(new String[]{getString(R.string.voice_introduction), getString(R.string.voice_assistance)},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    // start voice introduction
                                    startActivity(new Intent(getContext(), VoiceIntroActivity.class));
                                } else {
                                    // start voice assist
                                    startActivity(new Intent(getContext(), VoiceAssistActivity.class));
                                }
                            }
                        });
            }
        });

        // scenic introduction
        CustomHomeLeftGridItem introItem = view.findViewById(R.id.gi_home_left_scenic_intro);
        introItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ScenicAreaIntroActivity.class));
            }
        });

        //右上角定位信息
        TextView tv_fragment_home_left_pos = view.findViewById(R.id.tv_fragment_home_left_pos);
        tv_fragment_home_left_pos.setText("郑州大学");

        TextView tv_home_left_loading = view.findViewById(R.id.tv_home_left_loading);
        tv_home_left_loading.setVisibility(View.INVISIBLE);

    }

    /**
     * 启动一个带有取消按钮的列表对话框
     *
     * @param items    列表项
     * @param listener 列表项的监听器
     */
    private void fireDialog(String[] items, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(getContext())
                .setItems(items, listener)
                .show();
    }
}
