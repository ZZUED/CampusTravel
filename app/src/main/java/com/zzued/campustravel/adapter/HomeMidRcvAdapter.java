package com.zzued.campustravel.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzued.campustravel.R;
import com.zzued.campustravel.activity.ScenicSpotIntroActivity;

/**
 * HomePage 兴趣点推荐页 RecyclerView 的 Adapter
 */
public class HomeMidRcvAdapter extends RecyclerView.Adapter<HomeMidRcvAdapter.ViewHolder> {
    // todo 为适配器设置数据，根据数据设置 ViewHolder 内容 以及 监听器
    private Context context;

    public HomeMidRcvAdapter(Context context){
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.ll_rcv_home_mid_poi_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo add intent data
                context.startActivity(new Intent(context, ScenicSpotIntroActivity.class));
            }
        });
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
