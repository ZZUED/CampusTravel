package com.zzued.campustravel.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zzued.campustravel.R;
import com.zzued.campustravel.adapter.HomePagerAdapter;
import com.zzued.campustravel.view.CustomHomeBtmNavi;

import java.util.ArrayList;

public class HomePageActivity extends BaseActivity {
    private ArrayList<CustomHomeBtmNavi> btmNavis;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        viewPager = findViewById(R.id.vp_home_page);
        viewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager()));

        init();

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                if (btmNavis == null)
                    init();
                for (int i = 0; i < btmNavis.size(); i++) {
                    if (i == position)
                        btmNavis.get(i).setChecked();
                    else
                        btmNavis.get(i).setUnChecked();
                }
            }
        });
    }

    /**
     * 初始化 -- 尽量不在{@link #onCreate(Bundle)}中做太多工作
     */
    private void init() {
        btmNavis = new ArrayList<>(3);
        btmNavis.add((CustomHomeBtmNavi) findViewById(R.id.hbn_home_left));
        btmNavis.add((CustomHomeBtmNavi) findViewById(R.id.hbn_home_middle));
        btmNavis.add((CustomHomeBtmNavi) findViewById(R.id.hbn_home_right));
        for (int i = 0; i < btmNavis.size(); i++) {
            final int finalI = i;
            btmNavis.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(finalI);
                }
            });
        }
    }
}
