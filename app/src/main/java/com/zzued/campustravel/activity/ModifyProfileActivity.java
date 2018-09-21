package com.zzued.campustravel.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
        llPhone = findViewById(R.id.ll_modify_profile_phone);
        ivHead = findViewById(R.id.iv_modify_profile_head);
        tvName = findViewById(R.id.tv_modify_profile_name);
        tvPhone = findViewById(R.id.tv_modify_profile_phone);
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
                fireDialog(0);
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
                        tvBirth.setText(String.format(Locale.CHINA, "%4d-%02d-%02d", year, month, dayOfMonth));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIvHead(v);
            }
        });
        llPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireDialog(1);
            }
        });
    }


    public void setIvHead(View v){

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
        /*builder.setPositiveButton("flag1", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                ivHead.setImageResource(R.drawable.image1);
                //	提示信息
                Toast toast = Toast.makeText(getApplicationContext(), "你选择了覆盖", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        //	中间的按钮
        builder.setNeutralButton("flag2", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                ivHead.setImageResource(R.drawable.image2);
                //	提示信息
                Toast toast = Toast.makeText(getApplicationContext(), "你选择了跳过", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        //	第三个按钮
        builder.setNegativeButton("flag3", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                ivHead.setImageResource(R.drawable.image3);
                //	提示信息
                Toast toast = Toast.makeText(getApplicationContext(), "你选择了取消", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //	Diglog的显示*/
        //builder.create().show();
    }

    /**
     * 启动一个布局为一个 title+editText 的对话框
     * 用户通过此对话框更改姓名或手机号
     *
     * @param who 表示对话框由哪个对象启动
     *            0：修改名字，1：修改手机号
     */
    private void fireDialog(final int who) {
        final View view = LayoutInflater.from(this).inflate(R.layout.ll_modify_profile_dialog_et, null);
        final EditText editText = view.findViewById(R.id.et_modify_profile_dialog_content);
        TextView titleView = view.findViewById(R.id.tv_modify_profile_dialog_title);
        switch (who) {
            case 0:
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.setHint(tvName.getText());
                titleView.setText("修改用户名");
                break;
            case 1:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setHint(tvPhone.getText());
                titleView.setText("修改手机号");
        }
        new AlertDialog.Builder(this)
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
                        if (who == 0)
                            tvName.setText(editText.getText());
                        else if (who == 1)
                            tvPhone.setText(editText.getText());
                    }
                })
                .show();
    }
}
