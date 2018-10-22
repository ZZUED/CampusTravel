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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.idst.nls.NlsListener;
import com.alibaba.idst.nls.StageListener;
import com.zzued.campustravel.R;
import com.zzued.campustravel.constant.Constant;
import com.zzued.campustravel.util.AudioSoundRecognizer;
import com.zzued.campustravel.util.TextToSpeech;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VoiceAssistActivity extends BaseActivity {
    private static final String TAG = "VoiceAssistActivity";
    private static final int LEVEL_INIT = 5000;

    private Button btnSpeak;
    private Drawable micDrawable;
    private TextToSpeech a_play;
    private TextView tvIntroContent;

    private boolean recording = false;
    private AudioSoundRecognizer recognizer;

    private boolean playing = false;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String str = (String)msg.obj;
                    startPlay(str);
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
        tvIntroContent = findViewById(R.id.tv_voice_assist_content);

        a_play = new TextToSpeech(this, null);//实例化语音播放

        btnSpeak = findViewById(R.id.btn_voice_assist_speak);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(VoiceAssistActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(VoiceAssistActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
                    return;
                }
                if (playing)
                    stopPlay();
                else if (!recording)
                    startRecorder();
                else
                    stopRecorder();
            }
        });

        recognizer = new AudioSoundRecognizer(
                this,
                new NlsListener() {
                    @Override
                    public void onRecognizingResult(int i, RecognizedResult recognizedResult) {
                        if (i == 0)
                            try {
                                JSONObject jsonObject = new JSONObject(recognizedResult.asr_out);
//                                Toast.makeText(VoiceAssistActivity.this,
//                                        jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                                sendRecorder(jsonObject.getString("result"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        else
                            Log.e(TAG, "onRecognizingResult: error: " + i);
                    }
                },
                new StageListener() {
                    @Override
                    public void onVoiceVolume(int i) {
                        micDrawable.setLevel(i * 50 + LEVEL_INIT);
                    }
                });


    }

    /**
     * 用于发送识别出来的文本
     *
     * @param recorder 识别出来的文本
     */
    public void sendRecorder(String recorder){
        final String recorder_now = recorder;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(Constant.Url_VoiceAssistActivity + recorder_now)
                            .build();
                    Response response = client.newCall(request).execute();
                    String result = response.body().string();
                    Message msg = new Message();
                    msg.obj = result;
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //播放
    private void startPlay(String text) {
        a_play.start(text);
        playing = true;
        btnSpeak.setText(R.string.stop_introducing);
        if (tvIntroContent.getVisibility() != View.VISIBLE)
            tvIntroContent.setVisibility(View.VISIBLE);
        tvIntroContent.setText(text);
    }

    //停止介绍
    private void stopPlay() {
        a_play.stop();
        playing = false;
        btnSpeak.setText(R.string.click_and_speak);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length <= 0 || grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO))
                    Toast.makeText(this, "你就是头猪！", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "不能录音玩个鬼？！！！", Toast.LENGTH_SHORT).show();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startRecorder();
            }
        }
    }

    private void startRecorder() {
        Toast.makeText(VoiceAssistActivity.this, "正在录音", Toast.LENGTH_SHORT).show();
        btnSpeak.setText(getResources().getString(R.string.click_to_stop));
        recording = !recording;
        micDrawable.setLevel(LEVEL_INIT);
        recognizer.startRecognize();
    }

    private void stopRecorder() {
        btnSpeak.setText(getResources().getString(R.string.click_and_speak));
        recording = !recording;
        micDrawable.setLevel(0);
        recognizer.stopRecognize();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (playing)
            stopPlay();
        if (recording)
            stopRecorder();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        a_play.onDestroy();
    }
}
