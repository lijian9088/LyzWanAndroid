package com.lyz.lyzwanandroid.ui.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyz.lyzwanandroid.ui.listener.OnItemClickListener;

import butterknife.ButterKnife;

/**
 * @author liyanze
 * @create 2019/02/11
 * @Describe
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnItemClickListener itemClickListener;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public BaseViewHolder(@NonNull View itemView,OnItemClickListener listener) {
        this(itemView);
        itemView.setOnClickListener(this);
        setOnItemClickListener(listener);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            int position = getLayoutPosition();
            itemClickListener.onItemClick(v, position);
        }
    }

    protected void bind() {
    }

    protected void bind(int position) {
    }


}
