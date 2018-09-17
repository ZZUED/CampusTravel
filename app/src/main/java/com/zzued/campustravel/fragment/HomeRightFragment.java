package com.zzued.campustravel.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zzued.campustravel.R;
import com.zzued.campustravel.activity.BrowseHistoryActivity;
import com.zzued.campustravel.activity.WalletCouponActivity;
import com.zzued.campustravel.adapter.WalletCouponAdapter;

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

        return view;
    }

}
