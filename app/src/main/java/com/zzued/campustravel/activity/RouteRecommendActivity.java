package com.zzued.campustravel.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.MapView;
import com.amap.api.navi.model.NaviLatLng;
import com.zzued.campustravel.R;
import com.zzued.campustravel.util.PermissionHelper;
import com.zzued.campustravel.util.RouteShowHelper;

import java.util.ArrayList;

public class RouteRecommendActivity extends BaseActivity {
    private static final String TAG = "RouteRecommendActivity";
    private boolean permissionLocation = false;
    private boolean permissionStorage = false;
    private boolean permissionPhoneState = false;

    private MapView mapView;
    private RouteShowHelper routeShowHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_recommend);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        mapView = findViewById(R.id.map_route_rec_map);
        mapView.onCreate(savedInstanceState);

        permissionLocation = PermissionHelper.requestPermission(this, PermissionHelper.REQUEST_CODE_LOCATION);
        if (!permissionLocation)
            return;
        permissionStorage = PermissionHelper.requestPermission(this, PermissionHelper.REQUEST_CODE_STORAGE);
        if (!permissionStorage)
            return;
        permissionPhoneState = PermissionHelper.requestPermission(this, PermissionHelper.REQUEST_CODE_PHONE_STATE);
        if (!permissionPhoneState)
            return;
        init();
    }

    private void init(){
        routeShowHelper = new RouteShowHelper(this, mapView);
        Button btnRec = findViewById(R.id.btn_route_rec_start);
        btnRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildDialog(new String[]{
                        "郑州大学图书馆",
                        "郑州大学荷园",
                        "郑州大学核心教学区",
                        "郑州大学柳园",
                        "郑州大学南门",
                }, "郑州大学商业街");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int res = PermissionHelper.isPermissionGranted(this, requestCode, permissions, grantResults);
        switch (res) {
            case PermissionHelper.GRANTED_STORAGE:
            case PermissionHelper.GRANTED_PHONE_STATE:
            case PermissionHelper.GRANTED_LOCATION:
                permissionLocation = PermissionHelper.requestPermission(this, PermissionHelper.REQUEST_CODE_STORAGE);
                permissionStorage = PermissionHelper.requestPermission(this, PermissionHelper.REQUEST_CODE_STORAGE);
                permissionPhoneState = PermissionHelper.requestPermission(this, PermissionHelper.REQUEST_CODE_PHONE_STATE);
                break;
            case PermissionHelper.DENIED_LOCATION:
                Toast.makeText(this, "请授予地理位置权限", Toast.LENGTH_SHORT).show();
                PermissionHelper.requestPermission(this, PermissionHelper.REQUEST_CODE_LOCATION);
                break;
            case PermissionHelper.DENIED_STORAGE:
                Toast.makeText(this, "请授予存储权限", Toast.LENGTH_SHORT).show();
                PermissionHelper.requestPermission(this, PermissionHelper.REQUEST_CODE_STORAGE);
                break;
            case PermissionHelper.DENIED_PHONE_STATE:
                Toast.makeText(this, "请授予读取设备状态权限", Toast.LENGTH_SHORT).show();
                break;
            case PermissionHelper.FOREVER_DENIED_PHONE_STATE:
            case PermissionHelper.FOREVER_DENIED_LOCATION:
            case PermissionHelper.FOREVER_DENIED_STORAGE:
                Toast.makeText(this, "没权限没法玩老铁，设置->权限->打开 谢谢:-)", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        if (permissionStorage && permissionLocation)
            init();
    }

    /**
     * 构造推荐路线的景点选择对话框
     *
     * @param items 景点名称列表
     * @param pos   位置
     */
    private void buildDialog(String[] items, String pos) {
        if (items == null) {
            Log.e(TAG, "buildDialog: items null");
            return;
        }
        View view = LayoutInflater.from(this).inflate(R.layout.grid_route_recommend_multi_pick, null);
        TextView tvPos = view.findViewById(R.id.tv_view_route_rec_dialog_location);
        tvPos.setText(pos);
        GridLayout gridLayout = view.findViewById(R.id.gl_view_route_rec_dialog_options);
        // set layout parameters
        for (String item : items) {
            CheckBox box = new CheckBox(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1.0f);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1.0f);
            box.setLayoutParams(params);
            box.setText(item);
            gridLayout.addView(box);
        }
        new AlertDialog.Builder(this)
                .setView(view)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ArrayList<NaviLatLng> latLngs = new ArrayList<>();
                        latLngs.add(new NaviLatLng(34.8088062916, 113.5347681273));
                        latLngs.add(new NaviLatLng(34.8106362916, 113.5308061273));
                        latLngs.add(new NaviLatLng(34.8121112916, 113.5344351273));
                        latLngs.add(new NaviLatLng(34.8161042916, 113.5325041273));
                        latLngs.add(new NaviLatLng(34.8170892916, 113.5378581273));
                        latLngs.add(new NaviLatLng(34.8205498001, 113.5344626861));
                        latLngs.add(new NaviLatLng(34.8265608001, 113.5363356861));
                        routeShowHelper.draw(latLngs);
                    }
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        routeShowHelper.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
