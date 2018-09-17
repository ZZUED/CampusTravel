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

import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.zzued.campustravel.R;
import com.zzued.campustravel.adapter.SpotListAdapter;

import java.util.ArrayList;

public class ScenicAreaIntroActivity extends BaseActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenic_area_intro);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        Banner banner = findViewById(R.id.banner_scenic_area_spot);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setImageResource((Integer) path);
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView view = super.createImageView(context);
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return view;
            }
        });
        ArrayList<Integer> imgs = new ArrayList<>(4);
        imgs.add(R.drawable.img_boat_along_mei_lake);
        imgs.add(R.drawable.img_core_teaching_building);
        imgs.add(R.drawable.img_southern_gate);
        imgs.add(R.drawable.img_zzu_lib);
        banner.setImages(imgs);
        banner.start();

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
        rcvSpotList.setAdapter(new SpotListAdapter(this));

    }
}
