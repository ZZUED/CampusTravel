package com.zzued.campustravel.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.zzued.campustravel.R;

public class ThermalMapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermal_map);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));
    }
}
