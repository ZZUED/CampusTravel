package com.zzued.campustravel.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;

import com.zzued.campustravel.R;

import java.util.ArrayList;

public class RouteRecommendActivity extends BaseActivity {
    private static final String TAG = "RouteRecommendActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_recommend);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        TextView tvClickToPick = findViewById(R.id.tv_route_rec_pick_spot);
        tvClickToPick.setOnClickListener(new View.OnClickListener() {
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

    /**
     * 构造推荐路线的景点选择对话框
     *
     * @param items 景点名称列表
     * @param pos 位置
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
                        // todo check what has been selected and start recommend route here
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
