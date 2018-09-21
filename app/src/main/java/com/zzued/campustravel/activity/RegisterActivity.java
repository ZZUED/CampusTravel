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
                GregorianCalendar calendar = new GregorianCalendar();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        GregorianCalendar calendar = new GregorianCalendar();
                        int cury, curm, curd;
                        cury = calendar.get(Calendar.YEAR);
                        curm = calendar.get(Calendar.MONTH);
                        curd = calendar.get(Calendar.DAY_OF_MONTH);
                        if (y > cury || (y <= cury && m > curm) || (y <= cury && m <= curm && d > curd)){
                            Toast.makeText(RegisterActivity.this, "请选择合适的日期", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        year = y;
                        month = m;
                        day = d;
                        mTvBirth.setText(String.format(Locale.CHINA, "%04d-%02d-%02d", year, month + 1, day));
                    }
                }, year, month, day).show();
            }
        });

        mGetCertifyCode = findViewById(R.id.get_certify_code);
        mGetCertifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "获取验证码", Toast.LENGTH_SHORT).show();
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

}
