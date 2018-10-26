package com.zzued.campustravel.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzued.campustravel.R;
import com.zzued.campustravel.adapter.HomePagerAdapter;
import com.zzued.campustravel.util.Constant;
import com.zzued.campustravel.modelclass.Area;
import com.zzued.campustravel.modelclass.Spot;
import com.zzued.campustravel.util.MyApplication;
import com.zzued.campustravel.util.PermissionHelper;
import com.zzued.campustravel.util.WeatherHelper;
import com.zzued.campustravel.view.CustomHomeBtmNavi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomePageActivity extends BaseActivity {
    // 权限相关变量
    private boolean permissionLocation;
    private boolean permissionStorage;

    /**
     * 此对象为定位后的回调对象，通过 {@link #getMyLocation()} 方法访问
     * MY_LOCATION.getLatitude();//获取纬度
     * MY_LOCATION.getLongitude();//获取经度
     * PS:需要与 fragment 以通信更新数据
     */
    private static AMapLocation MY_LOCATION;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private static final String TAG = "HomePageActivity";

    private ArrayList<CustomHomeBtmNavi> btmNavis;
    private ViewPager viewPager;

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    List<Spot> spots = (List<Spot>) msg.obj;
                    HomePagerAdapter adapter = (HomePagerAdapter) viewPager.getAdapter();
                    adapter.getLeftFragment().setData(spots);
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    private static boolean posSent = false;
    private static String spotName = null;
    private static String spotIntroduce = null;
    private static String spotPictureUrl = null;
    private static double long_;
    private static double lat;

    private Handler handlertwo = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        Gson gson = new Gson();
                        Area area = gson.fromJson((String) msg.obj, Area.class);
                        spotName = area.get_ScenicAreaName();
                        spotIntroduce = area.get_SceniAreaIntro();
                        spotPictureUrl = area.get_PictureUrl();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
        setContentView(R.layout.activity_home_page);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        initPagerBtmNavi();

        permissionLocation = PermissionHelper.requestPermission(this, PermissionHelper.REQUEST_CODE_LOCATION);
        if (!permissionLocation)
            return;
        permissionStorage = PermissionHelper.requestPermission(this, PermissionHelper.REQUEST_CODE_STORAGE);
        if (!permissionStorage)
            return;

        WeatherHelper helper = new WeatherHelper();
        helper.sendWeatherNotify(this, new WeatherHelper.OnWeatherGotListener() {
            @Override
            public void onWeatherGot(String weatherDesc, String voiceDesc) {
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new Notification.Builder(HomePageActivity.this)
                        .setContentTitle("今日天气")
                        .setContentText(weatherDesc)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.icon_home_mid_weather)
                        .build();
                Objects.requireNonNull(manager).notify(1, notification);
            }
        });

        startLocate();
    }

    /**
     * 开启定位
     */
    private void startLocate() {
        Toast.makeText(this, "正在定位", Toast.LENGTH_SHORT).show();

        //初始化定位
        mLocationClient = new AMapLocationClient(MyApplication.getContext());

        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation == null || amapLocation.getErrorCode() != 0) {
                    Toast.makeText(HomePageActivity.this, "请打开网络与定位开关", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (MyApplication.DEBUG) {
                    Log.e(TAG, "onLocationChanged: 经纬度: " + amapLocation.getLatitude() + ", " + amapLocation.getLongitude());
                    Log.e(TAG, "onLocationChanged: debugging now, send no location info");
                    return;
                }
                setMyLocation(amapLocation);
                if (posSent)
                    return;
                sendMyLocation();
                posSent = true;
            }
        };
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        mLocationOption.setInterval(30000);

        //设置定位模式为AMapLocationMode.High_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 联动 viewpager 与 底部按钮
     */
    private void initPagerBtmNavi() {
        viewPager = findViewById(R.id.vp_home_page);
        viewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (btmNavis == null)
                    initPagerBtmNavi();
                for (int i = 0; i < btmNavis.size(); i++) {
                    if (i == position)
                        btmNavis.get(i).setChecked();
                    else
                        btmNavis.get(i).setUnChecked();
                }
            }
        });

        btmNavis = new ArrayList<>(3);
        btmNavis.add((CustomHomeBtmNavi) findViewById(R.id.hbn_home_left));
        btmNavis.add((CustomHomeBtmNavi) findViewById(R.id.hbn_home_middle));
        btmNavis.add((CustomHomeBtmNavi) findViewById(R.id.hbn_home_right));
        for (int i = 0; i < btmNavis.size(); i++) {
            final int finalI = i;
            btmNavis.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(finalI);
                }
            });
        }
    }

    /**
     * 获取定位后的位置信息
     *
     * @return 位置信息
     */
    public static AMapLocation getMyLocation() {
        return MY_LOCATION;
    }

    public static void setMyLocation(AMapLocation myLocation) {
        MY_LOCATION = myLocation;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int res = PermissionHelper.isPermissionGranted(this, requestCode, permissions, grantResults);
        switch (res) {
            case PermissionHelper.DENIED_LOCATION:
                Toast.makeText(this, "请授予地理位置权限", Toast.LENGTH_SHORT).show();
                PermissionHelper.requestPermission(this, PermissionHelper.REQUEST_CODE_LOCATION);
                break;
            case PermissionHelper.DENIED_STORAGE:
                Toast.makeText(this, "请授予存储权限", Toast.LENGTH_SHORT).show();
                PermissionHelper.requestPermission(this, PermissionHelper.REQUEST_CODE_STORAGE);
                break;
            case PermissionHelper.FOREVER_DENIED_LOCATION:
                Toast.makeText(this, "没权限没法玩老铁，设置->权限->打开 谢谢:-)", Toast.LENGTH_SHORT).show();
                break;
            case PermissionHelper.FOREVER_DENIED_STORAGE:
                Toast.makeText(this, "没权限没法玩老铁，设置->权限->打开 谢谢:-)", Toast.LENGTH_SHORT).show();
                break;
            case PermissionHelper.GRANTED_LOCATION:
                permissionLocation = true;
                if (!permissionStorage)
                    permissionStorage = PermissionHelper.requestPermission(this, PermissionHelper.REQUEST_CODE_STORAGE);
                break;
            case PermissionHelper.GRANTED_STORAGE:
                permissionStorage = true;
                if (!permissionLocation)
                    permissionLocation = PermissionHelper.requestPermission(this, PermissionHelper.REQUEST_CODE_LOCATION);
                break;
            default:
                break;
        }
        if (permissionStorage && permissionLocation)
            startLocate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();
    }

    public void sendMyLocation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(Constant.Url_HomePageActivity + "longitude=" +
                                    getMyLocation().getLongitude() + "&dimension=" + getMyLocation().getLatitude())
                            .build();
                    Response response = client.newCall(request).execute();
                    String spotDate = Objects.requireNonNull(response.body()).string();
                    try {
                        Gson gson = new Gson();
                        Message message = new Message();
                        message.obj = gson.fromJson(spotDate, new TypeToken<List<Spot>>() {
                        }.getType());
                        message.what = 1;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        long_ = getMyLocation().getLongitude();
        lat = getMyLocation().getLatitude();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void getAreaData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(Constant.Url_HomeLeftFragment)
                            .build();
                    Response response = client.newCall(request).execute();
                    String areaDate = Objects.requireNonNull(response.body()).string();
                    Message message = new Message();
                    message.obj = areaDate;
                    message.what = 1;
                    handlertwo.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static String getSpotName_() {
        return spotName;
    }

    public static String getSpotIntroduce_() {
        return spotIntroduce;
    }

    public static String getSpotPictureUrl_() {
        return spotPictureUrl;
    }

    public static double getLong_() {
        return long_;
    }

    public static double getLat() {
        return lat;
    }
}
