package com.zzued.campustravel.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zzued.campustravel.R;

import com.zzued.campustravel.activity.ModifyPasswordActivity;
import com.zzued.campustravel.activity.ModifyProfileActivity;
import com.zzued.campustravel.activity.SeeProfileActivity;

import com.zzued.campustravel.activity.StartActivity;
import com.zzued.campustravel.activity.WalletCouponActivity;

import com.zzued.campustravel.util.ActivityCollector;

import static android.content.Context.MODE_PRIVATE;


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


        SharedPreferences fromLogin = getActivity().getSharedPreferences("AccountAndPassWord", MODE_PRIVATE);
        String account= fromLogin.getString("emailAddress", "null");

        RelativeLayout rlMe = view.findViewById(R.id.rl_home_right_me);
        rlMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), SeeProfileActivity.class));
            }
        });

        TextView tv_home_right_name = rlMe.findViewById(R.id.tv_home_right_name);
        TextView tv_home_right_account = rlMe.findViewById(R.id.tv_home_right_account);
        ImageView head = rlMe.findViewById(R.id.iv_home_right_me);

        SharedPreferences headSP = getActivity().getSharedPreferences("Profile", MODE_PRIVATE);
        String headId= headSP.getString("emailAddress", "null");
        if(headId.equals("1")){
            head.setImageResource(R.drawable.img_modify_profile_head_1);
        }else if (head.equals("2")){
            head.setImageResource(R.drawable.img_modify_profile_head_2);
        }else if(head.equals("3")){
            head.setImageResource(R.drawable.img_modify_profile_head_3);
        }
        tv_home_right_name.setText(account);
        tv_home_right_account.setText(account);

        TextView coupon = view.findViewById(R.id.tv_home_right_coupon);
        coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WalletCouponActivity.class));
            }
        });

        TextView modifyPassword = view.findViewById(R.id.tv_home_right_modify_password);
        modifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ModifyPasswordActivity.class));
            }
        });


        TextView modifyProfile = view.findViewById(R.id.tv_home_right_modify_profile);
        modifyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ModifyProfileActivity.class));
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
                                //清空数据
                                SharedPreferences sp = getActivity().getSharedPreferences("AccountAndPassWord",MODE_PRIVATE);
                                sp.edit().clear().commit();
                                ActivityCollector.finishFromStart(sz);
                            }
                        })
                        .show();
            }
        });

        TextView aboutView = view.findViewById(R.id.tv_home_right_about);
        aboutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setView(R.layout.ll_home_right_about_dialog_view)
                        .show();
            }
        });

        return view;
    }


}
