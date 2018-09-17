package com.zzued.campustravel.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzued.campustravel.R;

import java.util.Objects;

public class VoiceInteractActivity extends BaseActivity {
    private boolean btnTagPaused = false;
    private Button btnStopVoice;
    private ImageView ivSpeaker;
    private TextView tvTite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_interact);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        tvTite = findViewById(R.id.tv_voice_inter_title);
        ivSpeaker = findViewById(R.id.iv_voice_inter_speaker);
        ivSpeaker.setImageResource(R.drawable.anim_list_voice_inter_speaker);
        ((AnimationDrawable) ivSpeaker.getDrawable()).start();

        btnStopVoice = findViewById(R.id.btn_voice_inter_stop);
        btnStopVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTagPaused = !btnTagPaused;
                if (btnTagPaused){
                    // todo stop introducing
                    ((AnimationDrawable) ivSpeaker.getDrawable()).stop();
                    ivSpeaker.setImageResource(R.drawable.ic_voice_inter_speaker_gray);
                    btnStopVoice.setText(getResources().getString(R.string.go_on_introducing));
                    tvTite.setText(R.string.voice_introducing_paused);
                }
                else {
                    // todo start introducing
                    ivSpeaker.setImageResource(R.drawable.anim_list_voice_inter_speaker);
                    ((AnimationDrawable) ivSpeaker.getDrawable()).start();
                    btnStopVoice.setText(getResources().getString(R.string.stop_introducing));
                    tvTite.setText(R.string.voice_introducing_now);
                }
            }
        });
    }

}
