package com.zzued.campustravel.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zzued.campustravel.R;

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
    }
}
