package com.zzued.campustravel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzued.campustravel.R;
import com.zzued.campustravel.modelclass.Coupon;

import java.util.ArrayList;
import java.util.Locale;

public class WalletCouponAdapter extends RecyclerView.Adapter<WalletCouponAdapter.ViewHolder> {
    private ArrayList<Coupon> coupons;
    private Context context;

    public WalletCouponAdapter(Context context, ArrayList<Coupon> coupons){
        this.coupons = coupons;
        this.context = context;
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
        holder.tvDiscount.setText(String.format("￥%s", coupons.get(position).getStoreDiscount()));
        holder.tvDeadline.setText(String.format(Locale.CHINA, "%d天后到期", coupons.get(position).getDeadline()));
    }

    @Override
    public int getItemCount() {
        return coupons.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDiscount;
        TextView tvDeadline;

        ViewHolder(View itemView) {
            super(itemView);
            tvDiscount = itemView.findViewById(R.id.tv_wallet_coupon_discount);
            tvDeadline = itemView.findViewById(R.id.tv_wallet_coupon_deadline);
        }
    }
}
