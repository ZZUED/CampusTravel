package com.zzued.campustravel.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class WeatherHelper {
    //天气URL
    private static final String WEATHER_URL = "http://t.weather.sojson.com/api/weather/city/101180101";

    private static final String SP_NAME = "day_weather_notify";
    private static final String SP_KEY_DAY = "day";

    private static String weather;
    private static String lowTemp;
    private static String highTemp;
    private static String sunrise;
    private static String sunset;

    private static boolean weatherGot = false;

    private OnWeatherGotListener weatherGotListener;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case -1:
                    Toast.makeText(MyApplication.getContext(), "获取天气数据失败", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    try {
                        JSONObject jsonObject = new JSONObject((String) msg.obj);
                        JSONArray array = jsonObject.getJSONObject("data").getJSONArray("forecast");
                        jsonObject = (JSONObject) array.get(0);
                        weather = jsonObject.getString("type");
                        lowTemp = jsonObject.getString("low").substring(3);
                        highTemp = jsonObject.getString("high").substring(3);
                        sunrise = jsonObject.getString("sunrise");
                        sunset = jsonObject.getString("sunset");
                        weatherGot = true;
                        if (weatherGotListener != null)
                            weatherGotListener.onWeatherGot(descWeather(), descWeatherVoice());
                    } catch (JSONException e) {
                        Toast.makeText(MyApplication.getContext(), "解析天气数据失败", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
            }
            return true;
        }
    });

    public WeatherHelper() {
    }

    /**
     * 描述天气
     *
     * @return 天气的描述
     */
    private static String descWeather() {
        return String.format(Locale.CHINA, "%s %s~%s", weather, lowTemp, highTemp);
    }

    private static String descWeatherVoice() {
        return String.format(Locale.CHINA, "您好，今日天气状况%s，最低气温 %s，最高气温 %s，日出时间 %s，日落时间 %s，祝您旅途愉快。", weather, lowTemp, highTemp, sunrise, sunset);
    }

    /**
     * 判断今日日期是否已经写入到 SP 中
     *
     * @param context context
     * @return true:写入, false:今日日期未写入
     */
    private static boolean isNotifySent(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        int storedDay = preferences.getInt(SP_KEY_DAY, -1);
        int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        if (storedDay == -1 || today != storedDay) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(SP_KEY_DAY, today);
            editor.apply();
            return false;
        }
        return true;
    }

    /**
     * 获取天气描述，若当前未获取天气，则先从网络获取
     *
     * @param weatherGotListener 获取到天气后的回调对象
     */
    public void getWeatherDesc(OnWeatherGotListener weatherGotListener) {
        this.weatherGotListener = weatherGotListener;
        if (weatherGot)
            weatherGotListener.onWeatherGot(descWeather(), descWeatherVoice());
        else
            getWeatherOnline();
    }

    /**
     * 发送天气通知
     *
     * @param context context
     */
    public void sendWeatherNotify(final Context context, OnWeatherGotListener weatherGotListener) {
        if (isNotifySent(context))
            return;
        getWeatherDesc(weatherGotListener);
    }

    /**
     * 使用 {@link OkHttpClient} 从网络获取天气状况
     */
    private void getWeatherOnline() {
        new OkHttpClient().newCall(
                new Request.Builder()
                        .url(WEATHER_URL)
                        .get()
                        .build()).enqueue(
                new Callback() {
                    Message message = new Message();

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                        message.what = -1;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) {
                        ResponseBody body = response.body();
                        message.what = 1;
                        try {
                            message.obj = Objects.requireNonNull(body).string();
                            handler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                            message.what = -1;
                            handler.sendMessage(message);
                        }
                    }
                });
    }

    public interface OnWeatherGotListener {
        void onWeatherGot(String weatherDesc, String voiceDesc);
    }

}
