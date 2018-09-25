package com.zzued.campustravel.util;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;
import android.util.Pair;

import com.alibaba.idst.nls.NlsClient;
import com.alibaba.idst.nls.NlsListener;
import com.alibaba.idst.nls.internal.protocol.NlsRequest;
import com.alibaba.idst.nls.internal.protocol.NlsRequestProto;

import java.util.ArrayDeque;

/**
 * 文字转语音类，正确使用方式：
 * 实例化 -> 实现接口（可选） -> 播放 -> 释放 {@link #onDestroy()}
 * ps: 在 activity 的 onDestroy() 方法中添加 {@link #onDestroy()}
 */
public class TextToSpeech {
    private static final String TAG = "TextToSpeech";

    /**
     * 阿里语音 api 相关
     */
    private static final String appKey = "nls-service";
    private static final String id = "LTAIHlDsOl6n7enI";
    private static final String secret = "YTpJe5bEwX15vLKvZmy3P7qWmL5oMk";

    private Context context;

    private NlsListener nlsListener;
    private NlsClient mNlsClient;
    private NlsRequest mNlsRequest;
    private static int iMinBufSize = AudioTrack.getMinBufferSize(
            8000,
            AudioFormat.CHANNEL_CONFIGURATION_STEREO,
            AudioFormat.ENCODING_PCM_16BIT
    );
    /**
     * 播放器 writeData 会阻塞主线程，因此在子线程中同步 writeData 操作
     * isPlaying: 判断播放器状态
     * audioTrack: 播放器
     * data: 数据队列，子线程从该队列取数据并写入播放器
     */
    private boolean isPlaying = false;
    private AudioTrack audioTrack;
    private final ArrayDeque<Pair<byte[], Integer>> data = new ArrayDeque<>();

    /**
     * 实例化该对象
     *
     * @param context  活动
     * @param listener 可选参数 - 文字转语音的回调对象
     *                 若为 null，则默认为播放声音
     *                 否则，回调此对象
     */
    public TextToSpeech(Context context, NlsListener listener) {
        this.context = context;
        audioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                8000,
                AudioFormat.CHANNEL_CONFIGURATION_STEREO,
                AudioFormat.ENCODING_PCM_16BIT,
                iMinBufSize,
                AudioTrack.MODE_STREAM);

        mNlsRequest = new NlsRequest(new NlsRequestProto(context));
        mNlsRequest.setApp_key(appKey);     //appkey请从 "快速开始" 帮助页面的appkey列表中获取
        mNlsRequest.initTts();               //初始化tts请求

        NlsClient.openLog(true);
        NlsClient.configure(context.getApplicationContext()); //全局配置
        if (listener != null)
            this.nlsListener = listener;
        else
            this.nlsListener = new NlsListener() {
                @Override
                public void onTtsResult(int status, byte[] ttsResult) {
                    switch (status) {
                        case NlsClient.ErrorCode.TTS_BEGIN:
                            // start audio track then add data into queue
                            Log.e(TAG, "onTtsResult: begin");
                            startAudioTrack();
                            addDataToAudioQueue(ttsResult, ttsResult.length);
                            break;
                        case NlsClient.ErrorCode.TTS_TRANSFERRING:
                            // transferring data, add data to the queue
                            Log.e(TAG, "onTtsResult: transferring");
                            if (!isPlaying)
                                return;
                            addDataToAudioQueue(ttsResult, ttsResult.length);
                            break;
                        case NlsClient.ErrorCode.TTS_OVER:
                            Log.e(TAG, "onTtsResult: over");
                            break;
                        case NlsClient.ErrorCode.CONNECT_ERROR:
                            Log.e(TAG, "onTtsResult: error");
                            break;
                    }
                }
            };
    }

    /**
     * 启动文本转语音
     *
     * @param input 待转换的文字
     */
    public void start(String input) {
        if (isPlaying) {
            Log.e(TAG, "start: audio is running, not able to start client again");
            return;
        }
        mNlsClient = NlsClient.newInstance(context, nlsListener, null, mNlsRequest);

        mNlsRequest.authorize(id, secret);      //请替换为用户申请到的数加认证key和密钥
        mNlsRequest.setTtsEncodeType("pcm");    //返回语音数据格式，支持pcm,wav.alaw
        mNlsRequest.setTtsVolume(50);           //音量大小默认50，阈值0-100
        mNlsRequest.setTtsSpeechRate(0);        //语速，阈值-500~500

        mNlsClient.checkService();
        mNlsClient.PostTtsRequest(input);       //用户输入文本
    }

    /**
     * 停止转换文本
     * 执行此操作会使播放器停止（区别于暂停）
     * 执行此操作之后再向播放器写入数据将不会有任何效果
     */
    public void stop() {
        stopAudioTrack(false);
        mNlsClient.cancel();
        mNlsClient.stop();
        isPlaying = false;
    }

    /**
     * 释放 audioTrack
     */
    public void onDestroy() {
        audioTrack.release();
    }

    /**
     * 启动播放器，并开启线程监听声音数据队列
     * 此方法数据流开始以及数据传输时调用，用以
     */
    private void startAudioTrack() {
        if (isPlaying)
            return;
        audioTrack.play();
        isPlaying = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isPlaying) {
                    Pair<byte[], Integer> tmpData;
                    synchronized (data) {
                        tmpData = data.poll();
                    }
                    if (tmpData != null)
                        audioTrack.write(tmpData.first, 0, tmpData.second);
                }
            }
        }).start();
    }

    /**
     * 将数据加入播放器队列
     *
     * @param ttsResult   数据
     * @param sizeInBytes 数据大小
     */
    private void addDataToAudioQueue(byte[] ttsResult, int sizeInBytes) {
        if (isPlaying) {
            synchronized (data) {
                data.add(new Pair<>(ttsResult, sizeInBytes));
            }
        } else {
            Log.e(TAG, "addDataToAudioQueue: audioTrack is not playing");
        }
    }

    /**
     * 停止播放器
     *
     * @param isPause 是否为暂停
     *                true：下次可以继续播放未播放的内容
     *                false：再次启动无法播放未播放内容
     */
    private void stopAudioTrack(boolean isPause) {
        if (isPlaying) {
            isPlaying = false;
            audioTrack.pause();
            if (!isPause)
                audioTrack.flush();
            synchronized (data) {
                data.clear();
            }
        } else {
            Log.e(TAG, "stopAudioTrack: audioTrack is not playing");
        }
    }
}
