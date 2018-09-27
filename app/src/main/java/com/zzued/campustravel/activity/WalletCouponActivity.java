package com.zzued.campustravel.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzued.campustravel.R;
import com.zzued.campustravel.adapter.WalletCouponAdapter;
import com.zzued.campustravel.constant.Constant;
import com.zzued.campustravel.modelclass.Coupon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WalletCouponActivity extends BaseActivity {
    private WalletCouponAdapter adapter;
    private ArrayList<Coupon> coupons;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    coupons.clear();
                    coupons.addAll((ArrayList<Coupon>) msg.obj);
                    adapter.notifyDataSetChanged();
                    break;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_coupon);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        coupons = new ArrayList<>();
        adapter = new WalletCouponAdapter(this, coupons);
        RecyclerView rcvWallet = findViewById(R.id.rcv_wallet_coupon_items);
        rcvWallet.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcvWallet.setAdapter(adapter);

        getCoupons();
    }

    private void getCoupons(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(Constant.Url_CouponActivity).build();
                    Response response = client.newCall(request).execute();
                    String res = response.body().string();
                    Message message = new Message();
                    message.what = 1;
                    message.obj = new Gson().fromJson(res, new TypeToken<List<Coupon>>(){}.getType());
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
