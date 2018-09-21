package com.zzued.campustravel.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zzued.campustravel.R;
import com.zzued.campustravel.util.ActivityCollector;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class RegisterActivity extends BaseActivity {

    private TextView mTvBirth, mTvArticle;
    private EditText mEtAccount, mEtPhone, mEtPassowrd, mEtPasswordConfirm;
    private RadioGroup mRgGender;
    private Button mBtnReg;
    private Button mGetCertifyCode;
    private int year, month, day;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setStatusBarColor(Color.WHITE);

        mBtnReg = findViewById(R.id.btn_reg_register);
        mBtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtAccount = findViewById(R.id.et_reg_account);
                mEtPassowrd = findViewById(R.id.et_reg_password);
                mEtPhone = findViewById(R.id.et_reg_phone);
                mEtPasswordConfirm = findViewById(R.id.et_reg_password_confirm);
                mRgGender = findViewById(R.id.rg_reg_gender);
                // todo check and register
                int num = ActivityCollector.size();
                startActivity(new Intent(RegisterActivity.this, HomePageActivity.class));
                ActivityCollector.finishFromStart(num);
            }
        });

        mTvBirth = findViewById(R.id.tv_reg_birth);
        mTvBirth.setText(getString(R.string.birthday_example));
        mTvBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateSetListener == null)
                    initListener();
                GregorianCalendar calendar = new GregorianCalendar();
                year = calendar.get(Calendar.YEAR) - 20;
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(RegisterActivity.this, dateSetListener, year, month, day).show();
            }
        });

        mGetCertifyCode = findViewById(R.id.get_certify_code);
        mGetCertifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "获取验证码", Toast.LENGTH_SHORT);
            }
        });

        mTvArticle = findViewById(R.id.tv_reg_article);
        mTvArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo show 协议条款 协议条款可以打开 web view 从服务器获取条款的网页
            }
        });
    }

    /**
     * 初始化监听器
     * 此方法可以在匿名内部类对象中调用
     * 在{@link #onCreate(Bundle)}函数中调用方法可能消耗活动的加载时间
     */
    private void initListener() {
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int y, int m, int d) {
                if (mTvBirth == null)
                    mTvBirth = findViewById(R.id.tv_reg_birth);
                year = y;
                month = m;
                day = d;
                mTvBirth.setText(String.format(Locale.CHINA, "%4d/%2d/%2d", year, month + 1, day));
            }
        };
    }


}
