package com.zzued.campustravel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzued.campustravel.R;
import com.zzued.campustravel.adapter.WalletCouponAdapter;

public class WalletCouponActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_coupon);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        RecyclerView rcvWallet = findViewById(R.id.rcv_wallet_coupon_items);
        rcvWallet.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcvWallet.setAdapter(new WalletCouponAdapter());
    }
}
