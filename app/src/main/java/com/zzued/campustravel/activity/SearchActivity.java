package com.zzued.campustravel.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzued.campustravel.R;
import com.zzued.campustravel.adapter.HomeMidRcvAdapter;
import com.zzued.campustravel.util.Constant;
import com.zzued.campustravel.modelclass.Spot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";
    private EditText etSearch;

    private String searchContent;
    private HomeMidRcvAdapter adapter;
    private ArrayList<Spot> spots;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    spots.clear();
                    spots.addAll((ArrayList<Spot>) msg.obj);
                    adapter.notifyDataSetChanged();
                    if (spots.size() <= 0)
                        Toast.makeText(SearchActivity.this, "未找到数据", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setStatusBarColor(getResources().getColor(R.color.colorAccent));

        ImageButton backButton = findViewById(R.id.img_btn_search_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etSearch = findViewById(R.id.et_search_search);

        RecyclerView rcv = findViewById(R.id.rcv_search_result);
        rcv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        spots = new ArrayList<>();
        adapter = new HomeMidRcvAdapter(this, spots);
        rcv.setAdapter(adapter);

        final TextView tvSearch = findViewById(R.id.tv_search_do_search);
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchContent = etSearch.getText().toString();
                if (TextUtils.isEmpty(searchContent)) {
                    Toast.makeText(SearchActivity.this, "请输入地点", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url(Constant.Url_SearchActivity + searchContent).build();
                            Response response = client.newCall(request).execute();
                            String res = response.body().string();
                            Log.e(TAG, "run: res: " + res);
                            Message message = new Message();
                            message.what = 1;
                            message.obj = new Gson().fromJson(res, new TypeToken<List<Spot>>(){}.getType());
                            handler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
