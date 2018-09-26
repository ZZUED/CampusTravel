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

import java.util.List;

public class SpotListAdapter extends RecyclerView.Adapter<SpotListAdapter.ViewHolder> {
    private Context context;
    private List<Spot> mSpotList;

    public SpotListAdapter(Context context,List<Spot> spotList){
        this.context = context;
        this.mSpotList = spotList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.ll_rcv_spot_list_item, parent, false);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        params.setMarginStart(15);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Spot spot = mSpotList.get(position);

        String strURL = spot.getPictureUrl();

        //showPicByVolleyImageLoader(context,strURL,holder.iv);
        Glide.with(context).load(strURL).placeholder(R.mipmap.ic_launcher).into(holder.iv);
        holder.tv.setText(spot.getScenicSpotName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo add spot info intent传递信息
                context.startActivity(new Intent(context, ScenicSpotIntroActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSpotList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv;
        ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_spot_list_item);
            tv = itemView.findViewById(R.id.tv_spot_list_item);
        }
    }
}
