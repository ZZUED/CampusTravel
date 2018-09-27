package com.zzued.campustravel.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.zzued.campustravel.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 路线规划类
 * 用于规划路线
 */
public class RouteShowHelper {
    private static final String TAG = "RouteShowHelper";

    private int ROUTE_NUM = 0;
    /**
     * 共有几个点
     */
    private static int TOTAL_ROUTE_NUM;

    private AMapNavi mAMapNavi;
    private AMap mAmap;

    private List<NaviLatLng> startList = new ArrayList<>();
    private List<NaviLatLng> endList = new ArrayList<>();
    /**
     * 保存当前算好的路线
     */
    private SparseArray<RouteOverLay> routeOverlays = new SparseArray<>();

    private LatLngBounds.Builder boundsBuilder = LatLngBounds.builder();

    private Context context;

    public RouteShowHelper(Context context, MapView mRouteMapView) {
        this.context = context;

        mAmap = mRouteMapView.getMap();
        mAmap.moveCamera(CameraUpdateFactory.zoomTo(15));

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
        mAmap.setMyLocationStyle(myLocationStyle);

        // 设置默认定位按钮是否显示，非必需设置。
        UiSettings uiSettings = mAmap.getUiSettings();
        uiSettings.setRotateGesturesEnabled(false);
        uiSettings.setMyLocationButtonEnabled(false);

        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mAmap.setMyLocationEnabled(true);

        // 移动视角
        mAmap.animateCamera(CameraUpdateFactory.changeLatLng(new LatLng(34.817186, 113.538692)));

        // 设置定位点半径颜色
        myLocationStyle.radiusFillColor(context.getResources().getColor(R.color.colorBlueTransparent));

//        mAmap.setMapType(AMap.MAP_TYPE_NAVI);
        try {
            mAMapNavi = AMapNavi.getInstance(context.getApplicationContext());
            if (mAMapNavi != null) {
                mAMapNavi.addAMapNaviListener(naviListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绘制路线
     *
     * @param points 待绘制的的路线的途径点（包括起始点）
     */
    public void draw(List<NaviLatLng> points) {
        clearRoute();
        startList.clear();
        endList.clear();
        TOTAL_ROUTE_NUM = points.size();
        for (int i = 0; i < TOTAL_ROUTE_NUM - 1; i++)
            startList.add(points.get(i));
        for (int i = 1; i < TOTAL_ROUTE_NUM; i++)
            endList.add(points.get(i));
        calculateRoute();
    }

    private void drawRoutes(int routeId, AMapNaviPath path) {
        RouteOverLay routeOverLay = new RouteOverLay(mAmap, path, context);
        routeOverLay.setTrafficLine(true);
        if (TOTAL_ROUTE_NUM == ROUTE_NUM) {
            if (ROUTE_NUM == 1) {
                routeOverLay.setStartPointBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_start));
                routeOverLay.setEndPointBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_end));
            } else {
                routeOverLay.setStartPointBitmap(null);
                routeOverLay.setEndPointBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_end));
            }
        } else {
            if (ROUTE_NUM == 1) {
                routeOverLay.setStartPointBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_start));
                routeOverLay.setEndPointBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_pass));
            } else {
                routeOverLay.setStartPointBitmap(null);
                routeOverLay.setEndPointBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_pass));
            }
        }
        routeOverLay.addToMap();
        routeOverlays.put(routeId + ROUTE_NUM, routeOverLay);
        if (TOTAL_ROUTE_NUM - 1 > ROUTE_NUM) {
//            routeOverLay.zoomToSpan(400);
            calculateRoute();
        }
        else {
            boundsBuilder.include(new LatLng(startList.get(0).getLatitude(), startList.get(0).getLongitude()));
            boundsBuilder.include(new LatLng(endList.get(TOTAL_ROUTE_NUM - 2).getLatitude(), endList.get(TOTAL_ROUTE_NUM - 2).getLongitude()));
            mAmap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 300));
        }
    }

    /**
     * 清除当前地图上算好的路线
     */
    private void clearRoute() {
        for (int i = 0; i < routeOverlays.size(); i++) {
            RouteOverLay routeOverlay = routeOverlays.valueAt(i);
            routeOverlay.setTrafficLine(true);
            routeOverlay.removeFromMap();
        }
        ROUTE_NUM = 0;
        routeOverlays.clear();
    }

    private void calculateRoute() {
        //clearRoute();
        int strategyFlag = 0;
        try {
            strategyFlag = mAMapNavi.strategyConvert(true, true, true, false, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (strategyFlag >= 0) {
            mAMapNavi.calculateWalkRoute(startList.get(ROUTE_NUM), endList.get(ROUTE_NUM));
            ROUTE_NUM++;
        }
    }

    public void onDestroy() {
        startList.clear();
        endList.clear();
        routeOverlays.clear();
        mAMapNavi.removeAMapNaviListener(naviListener);
    }

    private AMapNaviListener naviListener = new AMapNaviListener() {
        @Override
        public void onCalculateRouteSuccess(int[] ints) {
            HashMap<Integer, AMapNaviPath> paths = mAMapNavi.getNaviPaths();
            for (int anInt : ints) {
                AMapNaviPath path = paths.get(anInt);
                if (path != null) {
                    drawRoutes(anInt, path);
                }
            }

        }

        @Override
        public void onCalculateRouteFailure(int arg0) {
            Toast.makeText(MyApplication.getContext(), "计算路线失败，errorcode＝" + arg0, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onInitNaviFailure() {

        }

        @Override
        public void onInitNaviSuccess() {

        }

        @Override
        public void onStartNavi(int i) {

        }

        @Override
        public void onTrafficStatusUpdate() {

        }

        @Override
        public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

        }

        @Override
        public void onGetNavigationText(int i, String s) {

        }

        @Override
        public void onGetNavigationText(String s) {

        }

        @Override
        public void onEndEmulatorNavi() {

        }

        @Override
        public void onArriveDestination() {

        }

        @Override
        public void onReCalculateRouteForYaw() {

        }

        @Override
        public void onReCalculateRouteForTrafficJam() {

        }

        @Override
        public void onArrivedWayPoint(int i) {

        }

        @Override
        public void onGpsOpenStatus(boolean b) {

        }

        @Override
        public void onNaviInfoUpdate(NaviInfo naviInfo) {

        }

        @Override
        public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

        }

        @Override
        public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

        }

        @Override
        public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

        }

        @Override
        public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

        }

        @Override
        public void showCross(AMapNaviCross aMapNaviCross) {

        }

        @Override
        public void hideCross() {

        }

        @Override
        public void showModeCross(AMapModelCross aMapModelCross) {

        }

        @Override
        public void hideModeCross() {

        }

        @Override
        public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

        }

        @Override
        public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

        }

        @Override
        public void hideLaneInfo() {

        }

        @Override
        public void notifyParallelRoad(int i) {

        }

        @Override
        public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

        }

        @Override
        public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

        }

        @Override
        public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

        }

        @Override
        public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

        }

        @Override
        public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

        }

        @Override
        public void onPlayRing(int i) {

        }

        @Override
        public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {

        }

        @Override
        public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

        }

        @Override
        public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

        }
    };

}
