package com.zzued.campustravel.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.zzued.campustravel.R;
import com.zzued.campustravel.util.ActivityCollector;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {
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
                if (DEBUG){
                    int sz = ActivityCollector.size();
                    startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                    ActivityCollector.finishFromStart(sz);
                    return;
                }
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
                                    .url("http://maxerwinsmith.qicp.io:49291/login")
                                    .post(requestBody)
                                    .build();
                            Response response = client.newCall(request).execute();
                            String ss = response.body().string();
                            Message msg = new Message();
                            msg.obj = ss;
                            handler.sendMessage(msg);
                            switch (ss) {
                                case "1":
                                    //Toast.makeText(getApplicationContext(),"登陆成功",Toast.LENGTH_SHORT).show();
                                    editor = pref.edit();
                                    if (rememberPass.isChecked()) {//检查复选框是否被选中,选中的话就把账号密码保存下来
                                        //验证码暂时先保存，到时候再改
                                        editor.putBoolean("remember_password", true);
                                        editor.putString("account", account);
                                        editor.putString("password", password);
                                    } else {
                                        editor.clear();
                                    }
                                    editor.apply();
                                    int num = ActivityCollector.size();
                                    startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                                    ActivityCollector.finishFromStart(num);
                                    finish();
                                    break;
                                case "2":
                                    //Toast.makeText(getApplicationContext(),"账号不存在或错误",Toast.LENGTH_SHORT).show();
                                    etAccount.setText("");
                                    break;
                                default:
                                    //Toast.makeText(getApplicationContext(),"密码错误",Toast.LENGTH_SHORT).show();
                                    etPassword.setText("");
                                    break;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                //假设账号为admin，密码为123456，验证码为try
                /*if (account.equals("admin")&&password.equals("123456")){
                    editor = pref.edit();
                    if(rememberPass.isChecked()){//检查复选框是否被选中,选中的话就把账号密码保存下来
                        //验证码暂时先保存，到时候再改
                        editor.putBoolean("remember_password",true);
                        editor.putString("account",account);
                        editor.putString("password",password);
                    }else{
                        editor.clear();
                    }
                    editor.apply();
                    int num = ActivityCollector.size();
                    startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                    ActivityCollector.finishFromStart(num);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,
                            "account or password is not invalid",Toast.LENGTH_LONG).show();
                }*/
            }
        });
    }
}
