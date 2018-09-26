package com.zzued.campustravel.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzued.campustravel.R;
import com.zzued.campustravel.adapter.SpotListAdapter;
import com.zzued.campustravel.modelclass.Spot;

import java.util.ArrayList;
import java.util.List;

public class ScenicAreaIntroActivity extends BaseActivity {

    private List<Spot> spotList = new ArrayList<>();
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenic_area_intro);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));


        TextView tvSeeScenicMap = findViewById(R.id.tv_scenic_area_see_map);
        tvSeeScenicMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo show map, tell the area info
                startActivity(new Intent(ScenicAreaIntroActivity.this, FlatMapActivity.class));
            }
        });

        TextView tvComment = findViewById(R.id.tv_scenic_area_spot_comment_num);
        tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo show comment, dialog? or a new view? or nothing...
            }
        });

        // todo set data
        RecyclerView rcvSpotList = findViewById(R.id.rcv_scenic_area_spot_list);
        rcvSpotList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcvSpotList.setAdapter(new SpotListAdapter(this, new ArrayList<Spot>(2)));

    }
}
