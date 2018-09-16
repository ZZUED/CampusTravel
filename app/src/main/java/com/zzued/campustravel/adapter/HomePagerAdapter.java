package com.zzued.campustravel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zzued.campustravel.fragment.HomeLeftFragment;
import com.zzued.campustravel.fragment.HomeMiddleFragment;
import com.zzued.campustravel.fragment.HomeRightFragment;

public class HomePagerAdapter extends FragmentPagerAdapter {
    private HomeLeftFragment leftFragment;
    private HomeMiddleFragment middleFragment;
    private HomeRightFragment rightFragment;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
        leftFragment = new HomeLeftFragment();
        middleFragment = new HomeMiddleFragment();
        rightFragment = new HomeRightFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return leftFragment;
            case 1: return middleFragment;
            case 2: return rightFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    public HomeLeftFragment getLeftFragment() {
        return leftFragment;
    }

    public HomeMiddleFragment getMiddleFragment() {
        return middleFragment;
    }

    public HomeRightFragment getRightFragment() {
        return rightFragment;
    }
}
