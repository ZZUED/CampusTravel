package com.zzued.campustravel.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
import com.zzued.campustravel.constant.Constant;
import com.zzued.campustravel.view.CustomTitleBar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ModifyProfileActivity extends BaseActivity {
    private static final String TAG = "ModifyProfileActivity";

    private ImageView ivHead;

    private TextView tvName;
    private TextView tvBirth;

    private RadioGroup genderGroup;

    private String emailContent_modify;
    private String passwordContent_modify;
    private String headContent_modify;
    private String accountContent_modify;
    private String sexContent_modify;
    private String birthdayContent_modify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        CustomTitleBar titleBar = findViewById(R.id.title_modify_profile);
        LinearLayout llHead = findViewById(R.id.ll_modify_profile_head);
        LinearLayout llName = findViewById(R.id.ll_modify_profile_user_name);
        LinearLayout llBirth = findViewById(R.id.ll_modify_profile_birthday);
        ivHead = findViewById(R.id.iv_modify_profile_head);
        tvName = findViewById(R.id.tv_modify_profile_name);
        tvBirth = findViewById(R.id.tv_modify_profile_birthday);

        titleBar.setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectatRadio();
                accountContent_modify = tvName.getText().toString();
                Log.e(TAG, "onClick: name " + accountContent_modify);
                birthdayContent_modify = tvBirth.getText().toString().replace("/", "-");
                SharedPreferences fromLogin = getSharedPreferences("AccountAndPassWord", MODE_PRIVATE);
                emailContent_modify = fromLogin.getString("emailAddress", "null");
                passwordContent_modify = fromLogin.getString("password", "null");
                if (accountContent_modify != null &&
                        birthdayContent_modify != null &&
                        sexContent_modify != null &&
                        headContent_modify != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                OkHttpClient client = new OkHttpClient();
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("emailAddress", emailContent_modify)
                                        .add("password", passwordContent_modify)
                                        .add("headUrl", headContent_modify)
                                        .add("userName", accountContent_modify)
                                        .add("sex", sexContent_modify)
                                        .add("birthday", birthdayContent_modify)
                                        .build();
                                Request request = new Request.Builder()
                                        .url(Constant.Url_ModifyProfileActivity)
                                        .post(requestBody)
                                        .build();
                                Response response = client.newCall(request).execute();
                                String sss = response.body().string();
                                if (sss.equals("1")) {
                                    finish();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ModifyProfileActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    Toast.makeText(ModifyProfileActivity.this, "输入内容不完整", Toast.LENGTH_SHORT).show();
                }
            }
        });

        genderGroup = findViewById(R.id.rdgrp_modify_profile_gender);
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectatRadio();
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

    //获取RadioButton的内容
    private void selectatRadio() {
        int id = genderGroup.getCheckedRadioButtonId();
        sexContent_modify = String.valueOf(id == R.id.rdgrp_modify_profile_gender ? 1 : 0);
    }

    public void setIvHead() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyProfileActivity.this);
        builder.setIcon(R.drawable.ic_home_mid_gray);
        builder.setTitle("头像flag");
        builder.setMessage("请选择你要的头像的flag");
        View view = LayoutInflater.from(this).inflate(R.layout.ll_modify_profile_iv_head, null);
        //	第一个按钮
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        ImageView iv1 = view.findViewById(R.id.imageone);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHead.setImageResource(R.drawable.img_modify_profile_head_1);
                headContent_modify = "1";
                dialog.dismiss();
            }
        });

        ImageView iv2 = view.findViewById(R.id.imagetwo);
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHead.setImageResource(R.drawable.img_modify_profile_head_2);
                headContent_modify = "2";
                dialog.dismiss();
            }
        });

        ImageView iv3 = view.findViewById(R.id.imagethree);
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHead.setImageResource(R.drawable.img_modify_profile_head_3);
                headContent_modify = "3";
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
