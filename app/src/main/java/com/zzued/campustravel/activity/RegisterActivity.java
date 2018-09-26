package com.zzued.campustravel.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zzued.campustravel.R;
import com.zzued.campustravel.constant.Constant;
import com.zzued.campustravel.util.ActivityCollector;
import com.zzued.campustravel.util.MyApplication;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = "RegisterActivity";

    private TextView mTvBirth, mTvArticle;
    private EditText mEtAccount, mEtPassowrd, mEtCertifyCode, mEtPasswordConfirm;
    private RadioGroup mRgGender;
    private Button mBtnReg;
    private Button mGetCertifyCode;
    private int year, month, day;

    private String mEtAccountContent;
    private String mEtCertifyCodeContent;
    private String mEtPasswordContent;
    private String mEtPasswordConfirmContent;
    private String mTvBirthContent;
    private String radioButtonContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setStatusBarColor(Color.WHITE);

        mEtAccount = findViewById(R.id.et_reg_account);
        mBtnReg = findViewById(R.id.btn_reg_register);
        mBtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtAccount = findViewById(R.id.et_reg_account);
                mEtPassowrd = findViewById(R.id.et_reg_password);
                mEtCertifyCode = findViewById(R.id.et_reg_phone);
                mEtPasswordConfirm = findViewById(R.id.et_reg_password_confirm);
                mRgGender = findViewById(R.id.rg_reg_gender);
                mEtAccountContent = mEtAccount.getText().toString();
                mEtCertifyCodeContent = mEtCertifyCode.getText().toString();
                mEtPasswordContent = mEtPassowrd.getText().toString();
                mEtPasswordConfirmContent = mEtPasswordConfirm.getText().toString();
                mTvBirthContent = mTvBirth.getText().toString().replace("/", "-");

                if (MyApplication.DEBUG) {
                    int num = ActivityCollector.size();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    ActivityCollector.finishFromStart(num);
                    Log.e(TAG, "onClick: debugging now, no register");
                } else {
                    if (mEtAccountContent != null &&
                            mEtCertifyCodeContent != null &&
                            mEtPasswordContent != null &&
                            mEtPasswordConfirmContent != null &&
                            mTvBirthContent != null &&
                            radioButtonContent != null) {
                        if (isPasswordSame(mEtPassowrd.getText().toString(), mEtPasswordConfirm.getText().toString())) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        OkHttpClient client = new OkHttpClient();
                                        RequestBody requestBody = new FormBody.Builder()
                                                .add("emailAddress", mEtAccountContent)
                                                .add("password", mEtPasswordContent)
                                                .add("confirmPassword", mEtPasswordConfirmContent)
                                                .add("token", mEtCertifyCodeContent)
                                                .add("birthday", mTvBirthContent)
                                                .add("sex", radioButtonContent)
                                                .build();
                                        Request request = new Request.Builder()
                                                .url(Constant.Url_RigisterActivity_two)
                                                .post(requestBody)
                                                .build();
                                        Response response = client.newCall(request).execute();
                                        String ss = response.body().string();
                                        /*if(ss.equals("1")){
                                            SharedPreferences.Editor acc_flag = getSharedPreferences("name",
                                                    MODE_PRIVATE).edit();
                                            acc_flag.putString("userName", getStringRandom());
                                            acc_flag.apply();
                                        }*/
                                        int num = ActivityCollector.size();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        ActivityCollector.finishFromStart(num);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } else {
                            Toast.makeText(getApplicationContext(), "两个密码不一致", Toast.LENGTH_SHORT).show();
                            mEtPasswordConfirm.setText("");
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "有内容未填写", Toast.LENGTH_SHORT).show();
                    }
                }

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
                        if (y > cury || (y <= cury && m > curm) || (y <= cury && m <= curm && d > curd)) {
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
                sendRequestToGetCertifyCode();
            }
        });

        mTvArticle = findViewById(R.id.tv_reg_article);
        mTvArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 协议条款 协议条款可以打开 web view 从服务器获取条款的网页
            }
        });

        mRgGender = findViewById(R.id.rg_reg_gender);
        mRgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectRadio();
            }
        });
    }

    //获取RadioButton的内容
    private void selectRadio() {
        int id = mRgGender.getCheckedRadioButtonId();
        radioButtonContent = String.valueOf(id == R.id.radio_btn_female ? 1 : 0);
    }

    //正则表达式判断邮箱格式是否正确
    public boolean isEmailAddressRight(String s) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
        Matcher m = p.matcher(s);
        boolean b = m.matches();
        return b;
    }

    //请求获得验证码
    private void sendRequestToGetCertifyCode() {
        final String Eaddress = mEtAccount.getText().toString();
        if (isEmailAddressRight(Eaddress)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(Constant.Url_RigisterActivity_one + Eaddress)
                                .build();
                        Response response = client.newCall(request).execute();
                        String ss = response.body().string();
                        Toast.makeText(getApplicationContext(), "验证码已发送", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Toast.makeText(getApplicationContext(), "邮箱格式不正确", Toast.LENGTH_SHORT).show();
        }
    }

    //判断两次输入的密码是否一样
    public boolean isPasswordSame(String p1, String p2) {
        if (p1.equals(p2))
            return true;
        else {
            return false;
        }
    }

    //自动生成名字（中文）
    private static String getRandomYangHan() {
        int len = (int)(3+Math.random()*(10-3+1));
        String ret = "";
        for (int i = 0; i < len; i++) {
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                str = new String(b, "GBK"); // 转成中文
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            ret += str;
        }
        return ret;
    }

    //生成随机用户名，数字和字母组成,


    private String getStringRandom() {
        int length = (int)(3+Math.random()*(10-3+1));
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

}
