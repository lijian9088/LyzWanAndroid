package com.lyz.lyzwanandroid.ui.base.recyclerview;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.lyz.lyzwanandroid.ui.listener.OnItemClickListener;

/**
 * @author liyanze
 * @create 2019/02/11
 * @Describe
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected final Context context;
    public OnItemClickListener itemClickListener;

    public BaseViewHolder(ViewBinding binding) {
        super(binding.getRoot());
        context = itemView.getContext();
        itemView.setOnClickListener(this);
    }

//    public BaseViewHolder(@NonNull View itemView,OnItemClickListener listener) {
//        this(itemView);
//        itemView.setOnClickListener(this);
//        setOnItemClickListener(listener);
//    }

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
