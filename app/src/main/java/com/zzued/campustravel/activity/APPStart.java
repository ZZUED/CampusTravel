package com.zzued.campustravel.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


public class APPStart extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        firstRun();

    }

    private void firstRun() {
        SharedPreferences sharedPreferences = getSharedPreferences("FirstRun",0);
        Boolean first_run = sharedPreferences.getBoolean("First",true);
        if (first_run){
            sharedPreferences.edit().putBoolean("First",false).commit();
            Toast.makeText(this,"第一次", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(APPStart.this, APPGuide.class);
            startActivity(intent);
            this.finish();
        }
        else {
            Toast.makeText(this,"不是第一次", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(APPStart.this, StartActivity.class);
            startActivity(intent);
            this.finish();
        }
    }
}
