package com.zzued.campustravel.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzued.campustravel.R;

/**
 * 可自定义属性的标题栏
 */
public class CustomTitleBar extends RelativeLayout {
    private ImageButton imgBtn;
    private TextView titleView;
    private TextView rightTextView;

    public CustomTitleBar(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.rl_custom_titlerbar, this);
        imgBtn = findViewById(R.id.img_btn_view_title_back);
        titleView = findViewById(R.id.tv_view_title_title);
        rightTextView = findViewById(R.id.tv_view_title_right_text);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar);

        imgBtn.setImageResource(typedArray.getResourceId
                (R.styleable.CustomTitleBar_img_btn_src_title_bar, R.drawable.selector_view_back_gray_white));
        imgBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof Activity)
                    ((Activity)context).finish();
            }
        });
        titleView.setText(typedArray.getText(R.styleable.CustomTitleBar_title_title_bar));
        rightTextView.setText(typedArray.getText(R.styleable.CustomTitleBar_right_text_title_bar));

        titleView.setTextColor(typedArray.getColor(R.styleable.CustomTitleBar_text_color_title_bar, Color.BLACK));
        rightTextView.setTextColor(typedArray.getColor(R.styleable.CustomTitleBar_text_color_title_bar, Color.BLACK));
        typedArray.recycle();
    }

    /**
     * 设置返回键的监听器
     *
     * @param clickListener 监听器
     */
    public void setImgBtnClickListener(OnClickListener clickListener) {
        imgBtn.setOnClickListener(clickListener);
    }

    /**
     * 设置标题内容
     *
     * @param titleText 标题内容
     */
    public void setTitle(String titleText) {
        titleView.setText(titleText);
    }

    /**
     * 设置标题栏右侧文字内容及监听器
     *
     * @param text          右侧文字
     * @param clickListener 监听器
     */
    public void setRightText(String text, OnClickListener clickListener) {
        rightTextView.setText(text);
        rightTextView.setOnClickListener(clickListener);
    }
}
