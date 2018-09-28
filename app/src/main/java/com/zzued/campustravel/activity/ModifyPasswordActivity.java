package com.zzued.campustravel.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zzued.campustravel.R;
import com.zzued.campustravel.constant.Constant;
import com.zzued.campustravel.util.ActivityCollector;
import com.zzued.campustravel.view.CustomTitleBar;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ModifyPasswordActivity extends BaseActivity {
    private EditText etAccount;
    private EditText etOldPw;
    private EditText etNewPw;
    private EditText etRepeatPw;

    private TextView tvForget;

    private CustomTitleBar titleBar;
    private String account_e;
    private String oldpw_e;
    private String newpw_e;
    private String renewpw_e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        etAccount = findViewById(R.id.et_modify_password_account);
        etOldPw = findViewById(R.id.et_modify_password_old_passowrd);
        etNewPw = findViewById(R.id.et_modify_password_new_password);
        etRepeatPw = findViewById(R.id.et_modify_password_repeat);
        tvForget = findViewById(R.id.tv_modify_password_forget);

        titleBar = findViewById(R.id.title_modify_password);
        titleBar.setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account_e = etAccount.getText().toString();
                oldpw_e = etOldPw.getText().toString();
                newpw_e = etNewPw.getText().toString();
                renewpw_e = etRepeatPw.getText().toString();
                if (account_e != null && oldpw_e != null && newpw_e != null && renewpw_e != null) {
                    if (newpw_e.equals(renewpw_e)) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    OkHttpClient client = new OkHttpClient();
                                    RequestBody requestBody = new FormBody.Builder()
                                            .add("emailAddress", account_e)
                                            .add("oldPassword", oldpw_e)
                                            .add("newPassword", newpw_e)
                                            .build();
                                    Request request = new Request.Builder()
                                            .url(Constant.Url_ModifyPasswordActivity)
                                            .post(requestBody)
                                            .build();
                                    Response response = client.newCall(request).execute();
                                    String ss = response.body().string();
                                    if (ss.equals("1")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(ModifyPasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        int sz = ActivityCollector.size();
                                        startActivity(new Intent(ModifyPasswordActivity.this, LoginActivity.class));
                                        ActivityCollector.finishFromStart(sz);
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(ModifyPasswordActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    } else {
                        Toast.makeText(ModifyPasswordActivity.this, "两次填写的密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ModifyPasswordActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ModifyPasswordActivity.this)
                        .setMessage("相信你不会忘记的")
                        .show();
            }
        });
    }
}
