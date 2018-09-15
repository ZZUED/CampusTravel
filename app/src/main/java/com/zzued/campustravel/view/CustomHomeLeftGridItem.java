package com.zzued.campustravel.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzued.campustravel.R;

/**
 * {@link com.zzued.campustravel.activity.HomePageActivity} 第一页网格中的图标
 */
public class CustomHomeLeftGridItem extends LinearLayout {
    public CustomHomeLeftGridItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.ll_custom_home_left_grid_item, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomHomeLeftGridItem);

        ImageView image = findViewById(R.id.iv_view_home_grid_img);
        image.setImageResource(typedArray.getResourceId
                (R.styleable.CustomHomeLeftGridItem_img_src_home_grid, R.drawable.ic_launcher_background));

        TextView title = findViewById(R.id.tv_view_home_grid_title);
        title.setText(typedArray.getText(R.styleable.CustomHomeLeftGridItem_text_main_home_grid));

        TextView subTitle = findViewById(R.id.tv_view_home_grid_sub_title);
        subTitle.setText(typedArray.getText(R.styleable.CustomHomeLeftGridItem_text_sub_home_grid));

        typedArray.recycle();
    }
}
