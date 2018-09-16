package com.zzued.campustravel.activity;

import android.os.Bundle;

import com.zzued.campustravel.R;

public class ScenicSpotIntroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenic_spot_intro);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));
    }
}
