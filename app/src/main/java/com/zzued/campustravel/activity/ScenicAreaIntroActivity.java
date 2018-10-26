package com.zzued.campustravel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzued.campustravel.R;
import com.zzued.campustravel.adapter.SpotListAdapter;
import com.zzued.campustravel.modelclass.Spot;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.zzued.campustravel.util.Constant.Url_ScenicAreaIntroduceActivity;
import static com.zzued.campustravel.util.MyApplication.getContext;

public class ScenicAreaIntroActivity extends BaseActivity {

    private String name = "null";
    private String intro = "null";
    private String url = "null";

    private SpotListAdapter spot_listadapter;
    private List<Spot> spots = new ArrayList<>();

    private Handler handlerthree = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    spots.clear();
                    spots.addAll((ArrayList<Spot>) msg.obj);
                    spot_listadapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenic_area_intro);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        getNameAndArea();
        getDataForRecycleView();

        if (!name.equals("null") && !intro.equals("null") && !url.equals("null")) {
            ImageView imageView = findViewById(R.id.iv_scenic_area_spot);
            Glide.with(getBaseContext()).load(url).into(imageView);

            TextView textView = findViewById(R.id.tv_scenic_area_spot_name);
            textView.setText(name);

            TextView textView1 = findViewById(R.id.tv_scenic_area_spot_intro);
            textView1.setText(intro);

            spot_listadapter = new SpotListAdapter(getContext(), spots);
            RecyclerView rcvSpotList = findViewById(R.id.rcv_scenic_area_spot_list);
            rcvSpotList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            rcvSpotList.setAdapter(spot_listadapter);
        }

        TextView tvSeeScenicMap = findViewById(R.id.tv_scenic_area_see_map);
        tvSeeScenicMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScenicAreaIntroActivity.this, FlatMapActivity.class));
            }
        });

    }

    private void getNameAndArea() {
        Intent intent = getIntent();

        name = intent.getStringExtra("AreaName");
        intro = intent.getStringExtra("AreaIntroduce");
        url = intent.getStringExtra("AreaPicture");
    }

    private void getDataForRecycleView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(Url_ScenicAreaIntroduceActivity)
                            .build();
                    Response response = client.newCall(request).execute();
                    String spotDate = response.body().string();
                    try{
                        Gson gson = new Gson();
                        Message message = new Message();
                        message.obj = gson.fromJson(spotDate, new TypeToken<List<Spot>>() {}.getType());
                        message.what = 1;
                        handlerthree.sendMessage(message);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
