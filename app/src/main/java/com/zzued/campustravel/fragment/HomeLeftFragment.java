package com.zzued.campustravel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzued.campustravel.R;

/**
 * A simple {@link Fragment} subclass.
 * Left page inside viewpager in {@link com.zzued.campustravel.activity.HomePageActivity}
 */
public class HomeLeftFragment extends Fragment {

    public HomeLeftFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_left, container, false);
    }

}
