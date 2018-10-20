package com.zzued.campustravel.activity;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    private AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_map);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        mapView = findViewById(R.id.map_flat_map);
        mapView.onCreate(savedInstanceState);

        aMap = mapView.getMap();

        setLocationStyle();
        addMarkers();

        findViewById(R.id.btn_flat_back_to_my_pos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location location = aMap.getMyLocation();
                aMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
            }
        });
    }

    private void addMarkers() {
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81291199, 113.5356674)).title("泊月路"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.82134247, 113.5361176)).title("亭云路"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81037521, 113.5356064)).title("春华路"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.82346344, 113.5363007)).title("秋实路"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81753540, 113.5347595)).title("湖滨路"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.82517242, 113.5360031)).title("仁和大道"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81763458, 113.5377960)).title("天健大道"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81757355, 113.5339737)).title("厚德大道"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81560135, 113.5363998)).title("教学区南区"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81884384, 113.5365601)).title("教学区北区"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.82058716, 113.5334396)).title("菊园快递收发中心"));//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81510162, 113.5306625)).title("荷园二餐厅"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81401062, 113.5306015)).title("荷园一餐厅"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81510162, 113.5306625)).title("正味居"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.82072830, 113.5314941)).title("菊园秋穗园餐厅"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81299210, 113.5303955)).title("柳园风华园餐厅"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81174088, 113.5306931)).title("柳园聚英园餐厅"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81195450, 113.5306168)).title("柳园清真餐厅"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81147766, 113.5306854)).title("柳园同和昌食府餐厅"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.82255936, 113.5318069)).title("松园竞舸园餐厅"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.82289886, 113.5314407)).title("松园嵩阳食府餐厅"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81401443, 113.5303879)).title("荷园宿舍区"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.82055664, 113.5323563)).title("菊园宿舍区"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81107712, 113.5338669)).title("柳园宿舍区"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.82258606, 113.5329666)).title("松园宿舍区"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81449890, 113.5379944)).title("材料科学与工程学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81077576, 113.5388260)).title("电气工程学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81452560, 113.5393829)).title("管理工程学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81256866, 113.5403671)).title("化学与能源学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81461716, 113.5392151)).title("化学与分子工程学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81069183, 113.5403442)).title("机械工程学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81171036, 113.5404663)).title("建筑学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81083298, 113.5378952)).title("力学与工程学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81586075, 113.5386200)).title("生命科学学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81486511, 113.5380630)).title("数学与统计学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81335068, 113.5388184)).title("水利与环境学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81122208, 113.5406570)).title("土木工程学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81494141, 113.5387955)).title("物理工程学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81894302, 113.5407257)).title("法学院（知识产权学院）"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81843185, 113.5404663)).title("公共管理学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81927490, 113.5403595)).title("教育学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81857300, 113.5384521)).title("历史学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81821060, 113.5403366)).title("旅游管理学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81927109, 113.5406342)).title("马克思主义学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81925583, 113.5389252)).title("美术学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81815720, 113.5395966)).title("商学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81740189, 113.5324020)).title("体育学院（校本部）"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81768036, 113.5357513)).title("外语学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81817245, 113.5387573)).title("文学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81816483, 113.5387878)).title("新闻与传播学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81822586, 113.5401535)).title("信息管理学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.82391739, 113.5380325)).title("公共卫生学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.80613327, 113.5352097)).title("护理学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.82439041, 113.5372620)).title("基础医学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.82386780, 113.5390930)).title("药学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81203461, 113.5377274)).title("产业技术研究院（大学科技园）"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81274033, 113.5322342)).title("国际教育学院"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81270218, 113.5377808)).title("河南省资源与材料工业技术研究院（以下简称研究院）"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81508636, 113.5347214)).title("眉湖九博（河源）"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81563950, 113.5348053)).title("眉湖九博（问鼎）"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81621552, 113.5347137)).title("眉湖九博（博弈）"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81639481, 113.5346527)).title("眉湖九博（禅趣）"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81723404, 113.5349045)).title("眉湖九博（太和柱，和天烛）"));
//        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81778717, 113.5346603)).title("眉湖九博（观星测影）"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81624985, 113.5364456)).title("培英路"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81803131, 113.5365372)).title("毓秀路"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.82177353, 113.5316925)).title("大学生活动中心"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81114197, 113.5356674)).title("行政楼"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81678009, 113.5406418)).title("启明广场"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81741333, 113.5323486)).title("体育馆"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81713486, 113.5387192)).title("图书馆"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.80902481, 113.5355911)).title("五星广场"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81710052, 113.5371246)).title("元和广场"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.82633972, 113.5385590)).title("郑州大学校医院"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81728363, 113.5372925)).title("钟楼"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81735992, 113.5343552)).title("博雅湖（眉湖）"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.82260132, 113.5360260)).title("厚山"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81845093, 113.5349197)).title("眉湖九博（大道通渠）"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81938934, 113.5348434)).title("眉湖九博（凤台荷香）"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81338882, 113.5351868)).title("樱花林"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81491852, 113.5304413)).title("丁老头奶酪（郑大店）"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81483100, 113.5335500)).title("京东便利店（原校园风超市）"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81381989, 113.5337143)).title("蜜雪冰城（荷园商业街南侧）"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81282425, 113.5339355)).title("沁园春超市"));
        aMap.addMarker(new MarkerOptions().position(new LatLng(34.81658554, 113.5357437)).title("信息工程学院"));
    }

    /**
     * 设置定位样式
     */
    private void setLocationStyle() {

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
