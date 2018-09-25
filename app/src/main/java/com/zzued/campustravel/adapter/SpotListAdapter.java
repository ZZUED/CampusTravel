package com.zzued.campustravel.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzued.campustravel.R;
import com.zzued.campustravel.activity.ScenicSpotIntroActivity;
import com.zzued.campustravel.constant.Constant;
import com.zzued.campustravel.modelclass.Spot;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SpotListAdapter extends RecyclerView.Adapter<SpotListAdapter.ViewHolder> {
    private Context context;
    private List<Spot> mSpotList;

    public SpotListAdapter(Context context,List<Spot> spotList){
        this.context = context;
        this.mSpotList = spotList;
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.ll_rcv_spot_list_item, parent, false);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        params.setMarginStart(15);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Spot spot = mSpotList.get(position);
        String strURL = spot.getSpotPictureUrl();
        Bitmap bitmap = null;
        try {
            bitmap = getBitmap(strURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.iv.setImageBitmap(bitmap);
        holder.tv.setText(spot.getSpotName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo add spot info
                context.startActivity(new Intent(context, ScenicSpotIntroActivity.class));
            }
        });
    }

    //通过URL获取网络图片
    public static Bitmap getBitmap(String path) throws IOException {

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200){
            InputStream inputStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            conn.disconnect();
            return bitmap;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
