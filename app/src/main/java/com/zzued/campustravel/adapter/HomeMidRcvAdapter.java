package com.zzued.campustravel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzued.campustravel.R;

/**
 * HomePage 兴趣点推荐页 RecyclerView 的 Adapter
 */
public class HomeMidRcvAdapter extends RecyclerView.Adapter<HomeMidRcvAdapter.ViewHolder> {
    // todo 为适配器设置数据，根据数据设置 ViewHolder 内容 以及 监听器

    public HomeMidRcvAdapter(){

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.ll_rcv_home_mid_poi_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
