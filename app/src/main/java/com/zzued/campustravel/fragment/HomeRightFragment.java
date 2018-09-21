package com.zzued.campustravel.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzued.campustravel.R;
import com.zzued.campustravel.activity.BrowseHistoryActivity;
import com.zzued.campustravel.activity.ModifyPasswordActivity;
import com.zzued.campustravel.activity.ModifyProfileActivity;
import com.zzued.campustravel.activity.SeeProfileActivity;
import com.zzued.campustravel.activity.SettingActivity;
import com.zzued.campustravel.activity.StartActivity;
import com.zzued.campustravel.activity.WalletCouponActivity;
import com.zzued.campustravel.adapter.WalletCouponAdapter;
import com.zzued.campustravel.util.ActivityCollector;
import com.zzued.campustravel.view.CustomTitleBar;

/**
 * A simple {@link Fragment} subclass.
 * Right page inside viewpager in {@link com.zzued.campustravel.activity.HomePageActivity}
 */
public class HomeRightFragment extends Fragment {

    public HomeRightFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_right, container, false);
        TextView coupon = view.findViewById(R.id.tv_home_right_coupon);

        coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WalletCouponActivity.class));
            }
        });

        RelativeLayout rlMe = view.findViewById(R.id.rl_home_right_me);
        rlMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SeeProfileActivity.class));
            }
        });

        TextView modifyProfile = view.findViewById(R.id.tv_home_right_modify_profile);
        modifyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ModifyProfileActivity.class));
            }
        });

        TextView modifyPassword = view.findViewById(R.id.tv_home_right_modify_password);
        modifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ModifyPasswordActivity.class));
            }
        });

        TextView exitLogin = view.findViewById(R.id.tv_home_right_exit_login);
        exitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setMessage("退出当前账号?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int sz = ActivityCollector.size();
                                startActivity(new Intent(getContext(), StartActivity.class));
                                //Todo 记得清除缓存的密码
                                ActivityCollector.finishFromStart(sz);
                            }
                        })
                        .show();
            }
        });

        return view;
    }

}
