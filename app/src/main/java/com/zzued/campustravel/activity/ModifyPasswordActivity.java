package com.zzued.campustravel.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zzued.campustravel.R;
import com.zzued.campustravel.view.CustomTitleBar;

public class ModifyPasswordActivity extends BaseActivity {
    private EditText etAccount;
    private EditText etOldPw;
    private EditText etNewPw;
    private EditText etRepeatPw;

    private TextView tvForget;

    private CustomTitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        titleBar = findViewById(R.id.title_modify_password);
        titleBar.setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo send password data
                finish();
            }
        });

        etAccount = findViewById(R.id.et_modify_password_account);
        etOldPw = findViewById(R.id.et_modify_password_old_passowrd);
        etNewPw = findViewById(R.id.et_modify_password_new_password);
        etRepeatPw = findViewById(R.id.et_modify_password_repeat);
        tvForget = findViewById(R.id.tv_modify_password_forget);

        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo ??? what should I do ???
            }
        });
    }
}
