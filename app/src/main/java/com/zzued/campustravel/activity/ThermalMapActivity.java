package com.zzued.campustravel.activity;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.TileOverlayOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzued.campustravel.R;
import com.zzued.campustravel.util.Constant;
import com.zzued.campustravel.modelclass.ThermalData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ThermalMapActivity extends BaseActivity {
    private static final String TAG = "ThermalMapActivity";

    private MapView mapView;
    private AMap aMap;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    generateThermalMap((List<ThermalData>) msg.obj);
                    break;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermal_map);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        mapView = findViewById(R.id.map_thermal_map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        setLocationStyle(aMap);

        findViewById(R.id.btn_thermal_back_to_my_pos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location location = aMap.getMyLocation();
                if (location != null)
                    aMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
            }
        });

        requestThermalMapData();
    }

    /**
     * 设置定位样式
     *
     * @param aMap 地图
     */
    private void setLocationStyle(AMap aMap) {
        aMap.setMaxZoomLevel(16);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));

        aMap.getUiSettings().setMyLocationButtonEnabled(false);

        // 初始化定位蓝点样式类
        // myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）
        // 如果不设置myLocationType，默认也会执行此种模式。
        MyLocationStyle myLocationStyle = new MyLocationStyle();

        //连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);

        // 设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.interval(10000);

        // 设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);

        // 设置默认定位按钮是否显示，非必需设置。
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setRotateGesturesEnabled(false);
        uiSettings.setMyLocationButtonEnabled(true);

        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setMyLocationEnabled(true);

        // 移动视角
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(34.817109, 113.537297)));

        // 定位蓝点半径颜色
        myLocationStyle.radiusFillColor(getResources().getColor(R.color.colorBlueTransparent));

    }

    /**
     * 生成热力图
     */
    private void generateThermalMap(List<ThermalData> data) {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        for (ThermalData tmp : data) {
            tmp.setViews(tmp.getViews() / 10);
        }
        final double dif = 0.00000001;
        for (ThermalData tmp : data) {
            int k = 0;
            for (int i = 0; i < tmp.getViews(); i++, k++) {
                double idif = i * dif;
                if (k == 4) k = 0;
                switch (k) {
                    case 0:
                        latLngs.add(new LatLng(tmp.getLat() + idif, tmp.getLng()));
                        break;
                    case 1:
                        latLngs.add(new LatLng(tmp.getLat(), tmp.getLng() + idif));
                        break;
                    case 2:
                        latLngs.add(new LatLng(tmp.getLat() - idif, tmp.getLng()));
                        break;
                    case 3:
                        latLngs.add(new LatLng(tmp.getLat(), tmp.getLng() - idif));
                        break;
                }
            }
        }
        // 构建热力图 HeatmapTileProvider
        HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
        builder.data(latLngs) // 设置热力图绘制的数据
                .gradient(HeatmapTileProvider.DEFAULT_GRADIENT); // 设置热力图渐变，有默认值 DEFAULT_GRADIENT，可不设置该接口
        // Gradient 的设置可见参考手册
        // 构造热力图对象
        HeatmapTileProvider heatmapTileProvider = builder.build();
        // 初始化 TileOverlayOptions
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        // 设置瓦片图层的提供者
        tileOverlayOptions.tileProvider(heatmapTileProvider);
        // 向地图上添加 TileOverlayOptions 类对象
        aMap.addTileOverlay(tileOverlayOptions);
    }

    /**
     * 请求热力图数据
     */
    private void requestThermalMapData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String res = new OkHttpClient().newCall(new Request.Builder()
                            .url(Constant.Url_GetThermalMapData)
                            .get()
                            .build()).execute().body().string();

                    ArrayList<ThermalData> data = new Gson().fromJson(res, new TypeToken<ArrayList<ThermalData>>() {
                    }.getType());
                    Message message = new Message();
                    message.what = 1;
                    message.obj = data;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}
