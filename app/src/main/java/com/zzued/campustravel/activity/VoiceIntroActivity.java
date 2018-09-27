package com.zzued.campustravel.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zzued.campustravel.R;
import com.zzued.campustravel.constant.Constant;
import com.zzued.campustravel.modelclass.Intro;
import com.zzued.campustravel.util.TextToSpeech;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VoiceIntroActivity extends BaseActivity {
    private static final String TAG = "VoiceIntroActivity";
    private boolean btnTagPaused = false;
    private Button btnStopVoice;
    private ImageView ivSpeaker;
    private TextView tvTitle;
    private TextToSpeech tts;

    private double long_s;
    private double alti_s;

    private String text;

    private Handler handlerfour = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Intro intro = (Intro) msg.obj;
                    //text = intro.getScenicSpotIntroduce();
                    startPlay(intro.getScenicSpotIntroduce());
                    break;
                default:
                    break;
            }
            return true;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_intro);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        tvTitle = findViewById(R.id.tv_voice_inter_title);
        ivSpeaker = findViewById(R.id.iv_voice_inter_speaker);
        ivSpeaker.setImageResource(R.drawable.anim_list_voice_inter_speaker);
        ((AnimationDrawable) ivSpeaker.getDrawable()).start();
        getLongAndArea();

        btnStopVoice = findViewById(R.id.btn_voice_inter_stop);
        btnStopVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text == null)
                    getText();
                btnTagPaused = !btnTagPaused;
                if (btnTagPaused) {
                    stopPlay();
                    ((AnimationDrawable) ivSpeaker.getDrawable()).stop();
                    ivSpeaker.setImageResource(R.drawable.ic_voice_inter_speaker_gray);
                    btnStopVoice.setText(getResources().getString(R.string.start_introducing));
                    tvTitle.setText(R.string.voice_introducing_paused);
                } else {

                    startPlay(text);
                    ivSpeaker.setImageResource(R.drawable.anim_list_voice_inter_speaker);
                    ((AnimationDrawable) ivSpeaker.getDrawable()).start();
                    btnStopVoice.setText(getResources().getString(R.string.stop_introducing));
                    tvTitle.setText(R.string.voice_introducing_now);
                }
            }
        });
        init();
    }

    private void init() {
        tts = new TextToSpeech(this, null);
        //startPlay(text);
        getText();
        //把这里换了
    }

    private void startPlay(String text) {
        tts.start(text);
    }

    private void stopPlay() {
        tts.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        tts.onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.onDestroy();
    }

    private void getLongAndArea() {
        Intent intent = getIntent();
        long_s = intent.getDoubleExtra("long", 0);
        alti_s = intent.getDoubleExtra("alti", 0);
        Log.e(TAG, "getLongAndArea: long:" + long_s + " latitude:" + alti_s);
    }

    private void getText() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(Constant.Url_VoiceIntroActivity + "longitude=" +
                                    long_s + "&dimension=" + alti_s)//////语音讲解url
                            .build();
                    Response response = client.newCall(request).execute();
                    String spotDate = response.body().string();
                    Log.e(TAG, "run: spotDate: " + spotDate);
                    Gson gson = new Gson();
                    Message message = new Message();
                    message.obj = gson.fromJson(spotDate, Intro.class);
                    message.what = 1;
                    handlerfour.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
