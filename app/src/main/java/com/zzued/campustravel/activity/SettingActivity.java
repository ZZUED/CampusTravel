package com.zzued.campustravel.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zzued.campustravel.R;
import com.zzued.campustravel.util.ActivityCollector;

public class SettingActivity extends BaseActivity {
    private TextView modifyProfile;
    private TextView modifyPassword;
    private TextView notify;
    private TextView help;
    private TextView about;
    private Button exitLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        modifyPassword = findViewById(R.id.tv_setting_change_password);
        modifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ModifyPasswordActivity.class));
            }
        });

        modifyProfile = findViewById(R.id.tv_setting_modify_profile);
        modifyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ModifyProfileActivity.class));
            }
        });

        exitLogin = findViewById(R.id.btn_setting_exit_login);
        exitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SettingActivity.this)
                        .setMessage("退出当前账号?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int sz = ActivityCollector.size();
                                startActivity(new Intent(SettingActivity.this, StartActivity.class));
                                ActivityCollector.finishFromStart(sz);
                            }
                        })
                        .show();
            }
        });
    }
}
