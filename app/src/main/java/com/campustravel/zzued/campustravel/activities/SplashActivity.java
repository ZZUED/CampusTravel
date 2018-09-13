package com.campustravel.zzued.campustravel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;

import com.campustravel.zzued.campustravel.R;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);

        new CountDownTimer(1000, 1){
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashActivity.this, LogRegActivity.class));
                overridePendingTransition(android.R.anim.fade_in, R.anim.scale_fade_out);
                finish();
            }
        }.start();
    }
}
