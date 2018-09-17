package com.zzued.campustravel.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zzued.campustravel.R;

public class VoiceAssistActivity extends BaseActivity {
    private static final String TAG = "VoiceAssistActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_assist);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        Button btnSpeak = findViewById(R.id.btn_voice_assist_speak);
        btnSpeak.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e(TAG, "onLongClick: long click~");
                return true;
            }
        });
    }
}
