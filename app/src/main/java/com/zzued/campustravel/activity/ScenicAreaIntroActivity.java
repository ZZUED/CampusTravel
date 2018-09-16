package com.zzued.campustravel.activity;

import android.os.Bundle;

import com.zzued.campustravel.R;

public class ScenicAreaIntroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenic_area_intro);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));
    }
}
