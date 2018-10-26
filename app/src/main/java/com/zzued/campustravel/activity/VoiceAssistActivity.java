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
import com.zzued.campustravel.util.Constant;
import com.zzued.campustravel.util.AudioSoundRecognizer;
import com.zzued.campustravel.util.TextToSpeech;
import com.zzued.campustravel.util.WeatherHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VoiceAssistActivity extends BaseActivity {
    private static final String TAG = "VoiceAssistActivity";
    private static final int LEVEL_INIT = 4000;

    private Button btnSpeak;
    private Drawable micDrawable;
    private TextToSpeech a_play;
    private TextView tvIntroContent;
    private TextView recognizedText;

    private boolean recording = false;
    private AudioSoundRecognizer recognizer;

    private boolean playing = false;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String str = (String) msg.obj;
                    startPlay(str);
                    break;
                case 2:
                    startPlay((String) msg.obj);
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
        recognizedText = findViewById(R.id.tv_voice_assist_recognized_text);

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
                                String tmp = jsonObject.getString("result");
                                if (tmp.contains("天气")) {
                                    recognizedText.setText(tmp);
                                    new WeatherHelper().getWeatherDesc(new WeatherHelper.OnWeatherGotListener() {
                                        @Override
                                        public void onWeatherGot(String weatherDesc, String voiceDesc) {
                                            Message message = new Message();
                                            message.what = 2;
                                            message.obj = voiceDesc;
                                            handler.sendMessage(message);
                                        }
                                    });
                                }
                                else {
                                    tmp = extractHotWord(tmp);
                                    recognizedText.setText(tmp);
                                    sendRecorder(tmp);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        else {
                            Log.e(TAG, "onRecognizingResult: error: " + i);
                            recognizedText.setText("识别出错");
                        }
                    }
                },
                new StageListener() {
                    @Override
                    public void onVoiceVolume(int i) {
                        micDrawable.setLevel(i * (100 - LEVEL_INIT / 100) + LEVEL_INIT);
                    }
                });


    }

    /**
     * 判断目标字符串中是否包含经典列表
     *
     * @param target 目标字符串
     * @return 包含的景点列表字符串或原目标传
     */
    private String extractHotWord(String target) {
        if (target.equals("没和"))
            target = "博雅湖（眉湖)";
        List<String> scenicSpots = Arrays.asList("泊月路", "亭云路", "春华路", "秋实路", "湖滨路", "仁和大道", "天健大道", "厚德大道", "毓秀路", "大学生活动中心", "行政楼", "教学区南区", "教学区北区", "菊园快递收发中心", "启明广场", "体育馆", "图书馆", "五星广场", "元和广场", "郑州大学校医院", "钟楼", "博雅湖（眉湖）", "厚山", "眉湖九博（河源）", "眉湖九博（问鼎）", "眉湖九博（博弈）", "眉湖九博（禅趣）", "眉湖九博（太和柱，和天烛）", "眉湖九博（观星测影）", "眉湖九博（大道通渠）", "眉湖九博（凤台荷香）", "樱花林", "丁老头奶酪（郑大店）", "京东便利店（原校园风超市）", "蜜雪冰城（荷园商业街南侧）", "沁园春超市", "匆匆那年餐厅", "荷园二餐厅", "荷园一餐厅", "正味居", "菊园秋穗园餐厅", "柳园风华园餐厅", "柳园聚英园餐厅", "柳园清真餐厅", "柳园同和昌食府餐厅", "松园竞舸园餐厅", "松园嵩阳食府餐厅", "荷园宿舍区", "菊园宿舍区", "柳园宿舍区", "松园宿舍区", "材料科学与工程学院", "电气工程学院", "管理工程学院", "化学与能源学院", "化学与分子工程学院", "机械工程学院", "建筑学院", "力学与工程学院", "生命科学学院", "数学与统计学院", "水利与环境学院", "土木工程学院", "物理工程学院", "法学院（知识产权学院）", "公共管理学院", "教育学院", "历史学院", "旅游管理学院", "马克思主义学院", "美术学院", "商学院", "体育学院（校本部）", "外语学院", "文学院", "新闻与传播学院", "信息管理学院", "公共卫生学院", "护理学院", "基础医学院", "药学院", "产业技术研究院（大学科技园）", "国际教育学院", "河南省资源与材料工业技术研究院（以下简称研究院）", "信息工程学院");
        for (String sample : scenicSpots)
            if (target.contains(sample))
                return sample;
        return target;
    }

    /**
     * 用于发送识别出来的文本
     *
     * @param recorder 识别出来的文本
     */
    public void sendRecorder(String recorder) {
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
