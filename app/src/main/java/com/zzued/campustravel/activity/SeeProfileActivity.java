package com.zzued.campustravel.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zzued.campustravel.R;
import com.zzued.campustravel.constant.Constant;
import com.zzued.campustravel.modelclass.User;
import com.zzued.campustravel.util.ActivityCollector;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SeeProfileActivity extends BaseActivity {

    private ImageView see_head;
    private TextView see_name;
    private TextView see_data;
    private TextView see_email;
    private TextView see_sex;

    public String head;
    public String name;
    public String data;
    public String email;
    public String sex;

    private String getEmail;
    private String getPassword;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:
                    if(msg.obj!=null){
                        Gson gson = new Gson();
                        User user = gson.fromJson((String)msg.obj,User.class);
                        head = user.getUserPictureUrl();
                        name = user.getUserName();
                        data = user.getUserBirthday();
                        email = user.getUserEmail();
                        sex = user.getUserSex();
                        if(handler!=null){
                            see_name.setText(name);
                            see_data.setText(data);
                            see_email.setText(email);


                            if (sex.equals("0")){
                                see_sex.setText("男");
                            }else{
                                see_sex.setText("女");
                            }

                            if(head.equals("1")){
                                see_head.setImageResource(R.drawable.img_modify_profile_head_1);
                            }else if (head.equals("2")){
                                see_head.setImageResource(R.drawable.img_modify_profile_head_2);
                            }else{
                                see_head.setImageResource(R.drawable.img_modify_profile_head_3);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }

        }
    };;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_profile);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));
        see_head = findViewById(R.id.iv_see_profile_head);
        see_name = findViewById(R.id.tv_see_profile_name);
        see_data = findViewById(R.id.tv_see_profile_data);
        see_email = findViewById(R.id.tv_see_profile_email);
        see_sex = findViewById(R.id.tv_see_profile_sex);

        getProfileDate();
    }

    public void getProfileDate() {
        SharedPreferences fromLogin = getSharedPreferences("AccountAndPassWord", MODE_PRIVATE);
        getEmail= fromLogin.getString("emailAddress", "null");
        getPassword= fromLogin.getString("password", "null");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("emailAddress",getEmail)
                            .add("password",getPassword)
                            .build();
                    Request request = new Request.Builder()
                            .url(Constant.Url_SeeProfileActivity)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();
                    if(data!=null){
                        Message message = new Message();
                        message.obj = data;
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
