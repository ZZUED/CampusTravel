package com.zzued.campustravel.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


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
            sharedPreferences.edit().putBoolean("First",false).apply();
            Intent intent = new Intent(APPStart.this, APPGuide.class);
            startActivity(intent);
            this.finish();
        }
        else {
            SharedPreferences fromLogin = getSharedPreferences("AccountAndPassWord", MODE_PRIVATE);
            String account= fromLogin.getString("emailAddress", "null");
            String pass_word= fromLogin.getString("password", "null");

            //如果读到了账号密码不是“null”，那么直接登录
            if ((!account.equals("null"))&&(!pass_word.equals("null"))){
                Intent intent = new Intent(APPStart.this, HomePageActivity.class);
                startActivity(intent);
                this.finish();
            }else{
                Intent intent = new Intent(APPStart.this, StartActivity.class);
                startActivity(intent);
                this.finish();
            }
        }
    }
}
