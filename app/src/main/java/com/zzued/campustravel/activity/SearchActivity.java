package com.zzued.campustravel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zzued.campustravel.R;
import com.zzued.campustravel.adapter.HomeMidRcvAdapter;

public class SearchActivity extends BaseActivity {
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        ImageButton backButton = findViewById(R.id.img_btn_search_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo finish immediately or cancel some background task then finish
                finish();
            }
        });

        etSearch = findViewById(R.id.et_search_search);

        TextView tvSearch = findViewById(R.id.tv_search_do_search);
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo do search here, hide radio group?
            }
        });

        // todo set adapter for rcv
        RecyclerView rcv = findViewById(R.id.rcv_search_result);
        rcv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv.setAdapter(new HomeMidRcvAdapter(this));

    }
}
