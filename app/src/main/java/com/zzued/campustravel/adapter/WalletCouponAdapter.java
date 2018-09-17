package com.zzued.campustravel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzued.campustravel.R;

public class WalletCouponAdapter extends RecyclerView.Adapter<WalletCouponAdapter.ViewHolder> {
    // todo add data model of coupon

    public WalletCouponAdapter(){

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ll_rcv_coupon_item, parent, false);
        view.setElevation(8);
        view.setClickable(true);
        view.setFocusable(true);
        view.setBackground(parent.getContext().getResources().getDrawable(R.drawable.shape_wallet_coupon_rect_radius_bg_light));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
