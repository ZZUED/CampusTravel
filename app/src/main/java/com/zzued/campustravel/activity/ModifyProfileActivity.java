package com.zzued.campustravel.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zzued.campustravel.R;
import com.zzued.campustravel.view.CustomTitleBar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ModifyProfileActivity extends BaseActivity {
    private LinearLayout llHead;
    private LinearLayout llName;
    private LinearLayout llBirth;
    private LinearLayout llPhone;

    private ImageView ivHead;

    private TextView tvName;
    private TextView tvPhone;
    private TextView tvBirth;

    private RadioGroup genderGroup;

    private CustomTitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        titleBar = findViewById(R.id.title_modify_profile);
        llHead = findViewById(R.id.ll_modify_profile_head);
        llName = findViewById(R.id.ll_modify_profile_user_name);
        llBirth = findViewById(R.id.ll_modify_profile_birthday);
        ivHead = findViewById(R.id.iv_modify_profile_head);
        tvName = findViewById(R.id.tv_modify_profile_name);
        tvBirth = findViewById(R.id.tv_modify_profile_birthday);
        genderGroup = findViewById(R.id.rdgrp_modify_profile_gender);

        titleBar.setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo send modified data
                finish();
            }
        });


        llHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo fire a dialog
            }
        });

        llName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(ModifyProfileActivity.this).inflate(R.layout.ll_modify_profile_dialog_et, null);
                final EditText editText = view.findViewById(R.id.et_modify_profile_dialog_content);
                TextView titleView = view.findViewById(R.id.tv_modify_profile_dialog_title);
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.setHint(tvName.getText());
                titleView.setText("修改用户名");
                new AlertDialog.Builder(ModifyProfileActivity.this)
                        .setView(view)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tvName.setText(editText.getText());
                            }
                        })
                        .show();
            }
        });

        llBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GregorianCalendar calendar = new GregorianCalendar();
                new DatePickerDialog(
                        ModifyProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        GregorianCalendar calendar = new GregorianCalendar();
                        int cury, curm, curd;
                        cury = calendar.get(Calendar.YEAR);
                        curm = calendar.get(Calendar.MONTH);
                        curd = calendar.get(Calendar.DAY_OF_MONTH);
                        if (year > cury || (year <= cury && month > curm) || (year <= cury && month <= curm && dayOfMonth > curd)) {
                            Toast.makeText(ModifyProfileActivity.this, "请选择合适的日期", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        tvBirth.setText(String.format(Locale.CHINA, "%4d-%02d-%02d", year, month, dayOfMonth));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        llHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIvHead();
            }
        });
        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIvHead();
            }
        });
    }


    public void setIvHead() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyProfileActivity.this);
        builder.setIcon(R.drawable.ic_home_mid_gray);
        builder.setTitle("头像flag");
        builder.setMessage("请选择你要的头像的flag");
        View view = LayoutInflater.from(this).inflate(R.layout.iv_head, null);
        //	第一个按钮
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        ImageView iv1 = view.findViewById(R.id.imageone);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHead.setImageResource(R.drawable.image1);
                dialog.dismiss();
            }
        });

        ImageView iv2 = view.findViewById(R.id.imagetwo);
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHead.setImageResource(R.drawable.image2);
                dialog.dismiss();
            }
        });

        ImageView iv3 = view.findViewById(R.id.imagethree);
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHead.setImageResource(R.drawable.image3);
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
