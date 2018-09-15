package com.zzued.campustravel.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zzued.campustravel.R;
import com.zzued.campustravel.util.ActivityCollector;

public class LoginActivity extends BaseActivity {
    private EditText etAccount, etPassword, etVerifyCode;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setStatusBarColor(Color.WHITE);

        btnLogin = findViewById(R.id.btn_login_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etAccount = findViewById(R.id.et_login_account);
                etPassword = findViewById(R.id.et_login_password);
                etVerifyCode = findViewById(R.id.et_login_verify_code);
                // todo check and login
                int num = ActivityCollector.size();
                startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                ActivityCollector.finishFromStart(num);
            }
        });
    }
}
