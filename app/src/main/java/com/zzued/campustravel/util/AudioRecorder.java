package com.zzued.campustravel.util;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class AudioRecorder {
    public final static int VOICE_VOLUME = 1;
    public final static int RECORD_STOP = 2;

    private static final String TAG = "AudioRecorder";

    private static int sampleRateInHz = 8000;
    private static int channelConfig = AudioFormat.CHANNEL_IN_DEFAULT;
    private static int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

    private static AudioRecorder recorder;

    private int bufferSize;
    private AudioRecord record;

    private boolean on;

    private AudioRecorder() {
        on = false;
        record = null;
        bufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
    }

    /**
     * singleton
     * @return an AudioRecorder object
     */
    public static AudioRecorder getInstance() {
        if (recorder == null)
            recorder = new AudioRecorder();
        return recorder;
    }

    /**
     * start recorder
     * @param handler a handler to send back message for ui changing
     */
    public void start(final Handler handler) {
        if (record != null || on)
            return;
        record = new AudioRecord(MediaRecorder.AudioSource.MIC,
                sampleRateInHz,
                channelConfig,
                audioFormat,
                bufferSize);

        if (record.getState() == AudioRecord.STATE_UNINITIALIZED) {
            Log.e(TAG, "start: record state uninitialized");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                record.startRecording();
                on = true;
                while (on) {
                    short[] buffer = new short[bufferSize];
                    int sz = record.read(buffer, 0, bufferSize);
                    long v = 0;

                    for (int tmp : buffer)
                        v += tmp * tmp;
                    double vol = 10 * Math.log10(v / (double) sz);

                    Message message = new Message();
                    message.what = VOICE_VOLUME;
                    message.arg1 = (int) (vol * 50 + 3000);
                    handler.sendMessage(message);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                record.stop();
                record.release();
                record = null;

                Message message = new Message();
                message.what = RECORD_STOP;
                handler.sendMessage(message);
            }
        }).start();
    }

    /**
     * stop recording
     */
    public void stop() {
        on = false;
    }
}
