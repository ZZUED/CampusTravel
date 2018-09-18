package com.zzued.campustravel.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zzued.campustravel.R;
import com.zzued.campustravel.activity.BrowseHistoryActivity;
import com.zzued.campustravel.activity.SeeProfileActivity;
import com.zzued.campustravel.activity.SettingActivity;
import com.zzued.campustravel.activity.WalletCouponActivity;
import com.zzued.campustravel.adapter.WalletCouponAdapter;
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
        LinearLayout browseHistory = view.findViewById(R.id.ll_home_right_browse_history);
        LinearLayout coupon = view.findViewById(R.id.ll_home_right_coupon);

        browseHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BrowseHistoryActivity.class));
            }
        });
        coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WalletCouponActivity.class));
            }
        });

        CustomTitleBar titleBar = view.findViewById(R.id.title_home_right);
        titleBar.setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettingActivity.class));
            }
        });

        RelativeLayout rlMe = view.findViewById(R.id.rl_home_right_me);
        rlMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SeeProfileActivity.class));
            }
        });

        return view;
    }

}
