package com.zzued.campustravel.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.zzued.campustravel.activity.BaseActivity;

public class CustomViewpager extends ViewPager {
    private static final String TAG = "CustomViewpager";
    private boolean isInside;

    public CustomViewpager(Context context) {
        super(context);
    }

    public CustomViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
