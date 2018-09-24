package com.zzued.campustravel.activity;

import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.zzued.campustravel.R;

public class FlatMapActivity extends BaseActivity {
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_map);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        mapView = findViewById(R.id.map_flat_map);
        mapView.onCreate(savedInstanceState);

        AMap aMap = mapView.getMap();

        setLocationStyle(aMap);
    }

    /**
     * 设置定位样式
     * @param aMap 地图
     */
    private void setLocationStyle(AMap aMap){

        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));

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
        uiSettings.setMyLocationButtonEnabled(false);

        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setMyLocationEnabled(true);

        // 移动视角
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(34.817186, 113.538692)));

        // 设置定位点半径颜色
        myLocationStyle.radiusFillColor(getResources().getColor(R.color.colorBlueTransparent));

        /*
        图书馆
        34.817186, 113.538692
        钟楼
        34.817109, 113.537297
         */
        LatLng libLL = new LatLng(34.817186, 113.538692), bellLL = new LatLng(34.817109, 113.537297);
        aMap.addMarker(new MarkerOptions().position(libLL).title("图书馆").snippet("郑州大学图书馆"));
        aMap.addMarker(new MarkerOptions().position(bellLL).title("钟楼").snippet("郑大第一高度 - 钟楼"));
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
