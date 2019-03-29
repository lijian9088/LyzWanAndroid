package com.lyz.lyzwanandroid.ui.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.ui.base.BaseViewHolder;
import com.lyz.lyzwanandroid.ui.listener.OnItemClickListener;


/**
 * @author liyanze
 * @create 2019/03/25
 * @Describe
 */
public class TabHolder extends BaseViewHolder {

    private final TextView tv;

    public TabHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        setOnItemClickListener(listener);
        itemView.setOnClickListener(this);

        tv = itemView.findViewById(R.id.tv);
    }

    public void setText(String text) {
        tv.setText(text);
    }
}
