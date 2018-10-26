package com.zzued.campustravel.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zzued.campustravel.R;
import com.zzued.campustravel.util.Constant;
import com.zzued.campustravel.modelclass.SpotForIntroduce;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ScenicSpotIntroActivity extends BaseActivity {
    private int spotId;

    private ImageView ivImg;
    private TextView tvName;
    private TextView tvIntro;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    SpotForIntroduce spotForIntroduce = (SpotForIntroduce) msg.obj;
                    if (spotForIntroduce == null){
                        Toast.makeText(ScenicSpotIntroActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    Glide.with(ScenicSpotIntroActivity.this).load(spotForIntroduce._getPictureUrl()).placeholder(R.drawable.ic_launcher_background).into(ivImg);
                    tvIntro.setText(spotForIntroduce._getScenicSpotIntroduce());
                    tvName.setText(spotForIntroduce._getScenicSpotName());
                    break;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenic_spot_intro);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        ivImg = findViewById(R.id.iv_scenic_area_spot);
        tvIntro = findViewById(R.id.tv_scenic_area_spot_intro);
        tvName = findViewById(R.id.tv_scenic_area_spot_name);

        spotId = getIntent().getIntExtra("id", -1);
        if (spotId != -1){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(Constant.Url_ScenicSpotActivity + spotId).build();
                        Response response = client.newCall(request).execute();
                        String res = response.body().string();
                        Message message = new Message();
                        message.what = 1;
                        message.obj = new Gson().fromJson(res, SpotForIntroduce.class);
                        handler.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
