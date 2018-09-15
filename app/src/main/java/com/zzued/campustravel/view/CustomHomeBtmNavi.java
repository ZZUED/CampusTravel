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

public class CustomHomeBtmNavi extends LinearLayout {
    private TextView textView;
    private ImageView imageView;
    private int ivCheckedRes, ivUnCheckedRes;
    private boolean checked;

    public CustomHomeBtmNavi(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.ll_custom_home_bottom_nav, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomHomeBtmNavi);

        checked = typedArray.getBoolean(R.styleable.CustomHomeBtmNavi_checked_home_btm_nav, false);

        textView = findViewById(R.id.tv_view_home_bottom_nav);
        textView.setText(typedArray.getText(R.styleable.CustomHomeBtmNavi_text_home_btm_nav));
        ivUnCheckedRes = typedArray.getResourceId
                (R.styleable.CustomHomeBtmNavi_img_src_unchecked_home_btm_nav, R.drawable.ic_launcher_background);
        ivCheckedRes = typedArray.getResourceId
                (R.styleable.CustomHomeBtmNavi_img_src_checked_home_btm_nav, R.drawable.ic_launcher_background);
        imageView = findViewById(R.id.iv_view_home_bottom_nav);

        if (checked)
            setChecked();
        else
            setUnChecked();

        typedArray.recycle();
    }

    public void setChecked(){
        checked = true;
        imageView.setImageResource(ivCheckedRes);
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
    }
    public void setUnChecked(){
        checked = false;
        imageView.setImageResource(ivUnCheckedRes);
        textView.setTextColor(getResources().getColor(R.color.colorGray));
    }
}
