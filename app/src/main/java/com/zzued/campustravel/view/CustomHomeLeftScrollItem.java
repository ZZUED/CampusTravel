package com.zzued.campustravel.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzued.campustravel.R;

/**
 * 主页{@link com.zzued.campustravel.activity.HomePageActivity}下方的滚动列表
 */
public class CustomHomeLeftScrollItem extends LinearLayout {

    private TextView textView;
    private ImageView imageView;

    public CustomHomeLeftScrollItem(Context context){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.ll_custom_home_left_scroll_item, this);
    }

    public CustomHomeLeftScrollItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.ll_custom_home_left_scroll_item, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomHomeLeftScrollItem);

        textView = findViewById(R.id.tv_home_left_scroll_item);
        textView.setText(typedArray.getText(R.styleable.CustomHomeLeftScrollItem_text_home_left_scroll_item));
        imageView = findViewById(R.id.iv_home_left_scroll_item);
        imageView.setImageResource(typedArray.getResourceId(
                R.styleable.CustomHomeLeftScrollItem_img_src_home_left_scroll_item, R.drawable.ic_launcher_background
        ));
        typedArray.recycle();
    }

    /**
     * 设置文字
     *
     * @param text 文字
     */
    public void setText(String text) {
        textView.setText(text);
    }

    /**
     * 设置图片
     *
     * @param bitmap 图片
     */
    public void setImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
