package com.zzued.campustravel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzued.campustravel.R;

/**
 * A simple {@link Fragment} subclass.
 * Middle page inside viewpager in {@link com.zzued.campustravel.activity.HomePageActivity}
 */
public class HomeMiddleFragment extends Fragment {


    public HomeMiddleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_middle, container, false);
    }

}
