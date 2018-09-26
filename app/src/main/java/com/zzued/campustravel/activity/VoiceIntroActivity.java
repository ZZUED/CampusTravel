package com.zzued.campustravel.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzued.campustravel.R;
import com.zzued.campustravel.util.TextToSpeech;

public class VoiceIntroActivity extends BaseActivity {
    private boolean btnTagPaused = false;
    private Button btnStopVoice;
    private ImageView ivSpeaker;
    private TextView tvTitle;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_intro);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        tvTitle = findViewById(R.id.tv_voice_inter_title);
        ivSpeaker = findViewById(R.id.iv_voice_inter_speaker);
        ivSpeaker.setImageResource(R.drawable.anim_list_voice_inter_speaker);
        ((AnimationDrawable) ivSpeaker.getDrawable()).start();

        btnStopVoice = findViewById(R.id.btn_voice_inter_stop);
        btnStopVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTagPaused = !btnTagPaused;
                if (btnTagPaused){
                    stopPlay();
                    ((AnimationDrawable) ivSpeaker.getDrawable()).stop();
                    ivSpeaker.setImageResource(R.drawable.ic_voice_inter_speaker_gray);
                    btnStopVoice.setText(getResources().getString(R.string.go_on_introducing));
                    tvTitle.setText(R.string.voice_introducing_paused);
                }
                else {
                    startPlay(getResources().getString(R.string.zzu_intro));
                    ivSpeaker.setImageResource(R.drawable.anim_list_voice_inter_speaker);
                    ((AnimationDrawable) ivSpeaker.getDrawable()).start();
                    btnStopVoice.setText(getResources().getString(R.string.stop_introducing));
                    tvTitle.setText(R.string.voice_introducing_now);
                }
            }
        });
        init();
    }
    private void init(){
        tts = new TextToSpeech(this, null);
        startPlay(getResources().getString(R.string.zzu_intro));
    }
    private void startPlay(String text){
        tts.start(text);
    }
    private void stopPlay(){
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
}
