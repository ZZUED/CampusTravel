package com.zzued.campustravel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.zzued.campustravel.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new CountDownTimer(1000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashActivity.this, StartActivity.class));
                finish();
            }
        }.start();
    }

    @Override
    public void onBackPressed() {

    }
}
