package com.lyz.lyzwanandroid.ui.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.lyz.lyzwanandroid.R;

/**
 * @author liyanze
 * @create 2019/03/25
 * @Describe
 */
public class TagHolder extends RecyclerView.ViewHolder {

    public TextView tvTitle;
    public FlexboxLayout flexBox;

    public TagHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        flexBox = itemView.findViewById(R.id.flexBox);
        setIsRecyclable(false);
    }

}
