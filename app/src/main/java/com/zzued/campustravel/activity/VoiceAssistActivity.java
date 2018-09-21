package com.zzued.campustravel.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zzued.campustravel.R;
import com.zzued.campustravel.util.AudioRecorder;

public class VoiceAssistActivity extends BaseActivity {
    private static final String TAG = "VoiceAssistActivity";

    private Button btnSpeak;
    private Drawable micDrawable;
    private boolean recording = false;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case AudioRecorder.RECORD_STOP:
                    micDrawable.setLevel(0);
                    break;
                case AudioRecorder.VOICE_VOLUME:
                    micDrawable.setLevel(msg.arg1);
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
        setContentView(R.layout.activity_voice_assist);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        micDrawable = ((ImageView) findViewById(R.id.iv_voice_assist_micro_phone)).getDrawable();
        btnSpeak = findViewById(R.id.btn_voice_assist_speak);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(VoiceAssistActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(VoiceAssistActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
                    return;
                }
                if (!recording)
                    startRecorder();
                else
                    stopRecorder();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length <= 0 || grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "不能录音玩个鬼？！！！", Toast.LENGTH_SHORT).show();
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO))
                    Toast.makeText(this, "你就是头猪！", Toast.LENGTH_SHORT).show();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startRecorder();
            }
        }
    }

    private void startRecorder() {
        Toast.makeText(VoiceAssistActivity.this, "正在录音", Toast.LENGTH_SHORT).show();
        btnSpeak.setText(getResources().getString(R.string.click_to_stop));
        AudioRecorder.getInstance().start(handler);
        recording = !recording;
    }

    private void stopRecorder() {
        Toast.makeText(VoiceAssistActivity.this, "已停止录音", Toast.LENGTH_SHORT).show();
        btnSpeak.setText(getResources().getString(R.string.click_and_speak));
        AudioRecorder.getInstance().stop();
        recording = !recording;
    }


}
