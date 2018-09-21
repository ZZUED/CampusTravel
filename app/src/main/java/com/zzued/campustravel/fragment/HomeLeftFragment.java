package com.zzued.campustravel.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zzued.campustravel.R;
import com.zzued.campustravel.activity.FlatMapActivity;
import com.zzued.campustravel.activity.RouteRecommendActivity;
import com.zzued.campustravel.activity.ScenicAreaIntroActivity;
import com.zzued.campustravel.activity.SearchActivity;
import com.zzued.campustravel.activity.ThermalMapActivity;
import com.zzued.campustravel.activity.VoiceAssistActivity;
import com.zzued.campustravel.activity.VoiceInteractActivity;
import com.zzued.campustravel.adapter.SpotListAdapter;
import com.zzued.campustravel.view.CustomHomeLeftGridItem;

/**
 * A simple {@link Fragment} subclass.
 * Left page inside viewpager in {@link com.zzued.campustravel.activity.HomePageActivity}
 */
public class HomeLeftFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_left, container, false);
        initListener(view);

        final NestedScrollView holder =view.findViewById(R.id.ll_home_left_scroll_holder);
        holder.setVisibility(View.GONE);
        final ProgressBar progress = view.findViewById(R.id.progress_home_left);
        new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Integer... integers) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                progress.setVisibility(View.GONE);
                holder.setVisibility(View.VISIBLE);
            }
        }.execute(0, 0, 0);

        // todo set data of rcv
        RecyclerView rcvSpotList = view.findViewById(R.id.rcv_home_left_spot);
        rcvSpotList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        rcvSpotList.setAdapter(new SpotListAdapter(getContext()));

        return view;
    }

    /**
     * 初始化
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
                // start search activity
                startActivity(new Intent(getContext(), SearchActivity.class));
                getActivity().overridePendingTransition(Animation.ABSOLUTE, android.R.anim.fade_out);
            }
        });

        // map type
        CustomHomeLeftGridItem mapItem = view.findViewById(R.id.gi_home_left_map);
        mapItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildDialog(new String[]{getString(R.string.see_thermal_map), getString(R.string.see_flat_map)},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    // see thermal map
                                    startActivity(new Intent(getContext(), ThermalMapActivity.class));
                                } else {
                                    // see flat map
                                    startActivity(new Intent(getContext(), FlatMapActivity.class));
                                }
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
                buildDialog(new String[]{getString(R.string.voice_introduction), getString(R.string.voice_assistance)},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    // start voice introduction
                                    startActivity(new Intent(getContext(), VoiceInteractActivity.class));
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
    }

    /**
     * 启动一个带有取消按钮的列表对话框
     *
     * @param items    列表项
     * @param listener 列表项的监听器
     */
    private void buildDialog(String[] items, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(getContext())
                .setItems(items, listener)
                .show();
    }
}
