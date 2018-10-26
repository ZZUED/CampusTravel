package com.zzued.campustravel.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zzued.campustravel.R;
import com.zzued.campustravel.activity.ScenicSpotIntroActivity;
import com.zzued.campustravel.modelclass.Spot;

import java.util.ArrayList;

/**
 * HomePage 兴趣点推荐页 RecyclerView 的 Adapter
 */
public class HomeMidRcvAdapter extends RecyclerView.Adapter<HomeMidRcvAdapter.ViewHolder> {
    private ArrayList<Spot> spotList;
    private Context context;

    public HomeMidRcvAdapter(Context context, ArrayList<Spot> spots){
        this.context = context;
        this.spotList = spots;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.ll_rcv_home_mid_poi_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int tmp = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ScenicSpotIntroActivity.class);
                intent.putExtra("id", spotList.get(tmp).getScenicSpotId());
                context.startActivity(intent);
            }
        });
        holder.tvName.setText(spotList.get(position).getScenicSpotName());
        Glide.with(context).load(spotList.get(position).getPictureUrl()).placeholder(R.drawable.ic_launcher_background).into(holder.ivImg);
    }

    @Override
    public int getItemCount() {
        return spotList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivImg;
        TextView tvName;

        ViewHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.img_rcv_home_mid_scene_img);
            tvName = itemView.findViewById(R.id.tv_rcv_home_mid_scene_name);
        }
    }
}
