package com.zzued.campustravel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzued.campustravel.R;
import com.zzued.campustravel.adapter.BrowseHistoryAdapter;

public class BrowseHistoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_history);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        RecyclerView rcvBrowseHistory = findViewById(R.id.rcv_browse_history_items);
        rcvBrowseHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcvBrowseHistory.setAdapter(new BrowseHistoryAdapter());
    }
}
