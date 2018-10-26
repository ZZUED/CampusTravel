package com.zzued.campustravel.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.zzued.campustravel.R;
import com.zzued.campustravel.util.Constant;
import com.zzued.campustravel.util.ActivityCollector;
import com.zzued.campustravel.util.MyApplication;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private EditText etAccount;
    private EditText etPassword;
    private Button btnLogin;
    private CheckBox rememberPass;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.obj.equals("1")) {
                Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
            } else if (msg.obj.equals("2")) {
                Toast.makeText(getApplicationContext(), "账号不存在或错误", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setStatusBarColor(Color.WHITE);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        etAccount = findViewById(R.id.et_login_account);
        etPassword = findViewById(R.id.et_login_password);
        rememberPass = findViewById(R.id.remember_pass);
        btnLogin = findViewById(R.id.btn_login_login);

        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            //账号和密码设置到文本框中
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            etAccount.setText(account);
            etPassword.setText(password);
            rememberPass.setChecked(true);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.DEBUG) {
                    int sz = ActivityCollector.size();
                    startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                    ActivityCollector.finishFromStart(sz);
                    Log.e(TAG, "onClick: debugging now, no need to login");
                } else {
                    final String account = etAccount.getText().toString();
                    final String password = etPassword.getText().toString();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                OkHttpClient client = new OkHttpClient();
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("emailAddress", account)
                                        .add("password", password)
                                        .build();
                                Request request = new Request.Builder()
                                        .url(Constant.Url_LoginActivity)
                                        .post(requestBody)
                                        .build();
                                Response response = client.newCall(request).execute();
                                String ss = response.body().string();
                                Message msg = new Message();
                                msg.obj = ss;
                                handler.sendMessage(msg);
                                switch (ss) {
                                    case "1":
                                        //登陆成功
                                        editor = pref.edit();
                                        if (rememberPass.isChecked()) {
                                            editor.putBoolean("remember_password", true);
                                            editor.putString("emailAddress", account);
                                            editor.putString("password", password);
                                        } else {
                                            editor.clear();
                                        }
                                        editor.apply();

                                        SharedPreferences.Editor acc_pass = getSharedPreferences("AccountAndPassWord",
                                                MODE_PRIVATE).edit();
                                        acc_pass.putString("emailAddress", account);
                                        acc_pass.putString("password", password);
                                        acc_pass.apply();

                                        int num = ActivityCollector.size();
                                        startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                                        ActivityCollector.finishFromStart(num);
                                        finish();
                                        break;
                                    case "2":
                                        //账号不存在或错误
                                        etAccount.setText("");
                                        break;
                                    default:
                                        //密码错误
                                        etPassword.setText("");
                                        break;
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }

            }
        });
    }
}
