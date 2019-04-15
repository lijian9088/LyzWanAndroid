package com.lyz.lyzwanandroid.ui.adpter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.ui.base.BaseRecyclerViewAdapter;
import com.lyz.lyzwanandroid.ui.base.BaseViewHolder;
import com.lyz.lyzwanandroid.ui.listener.OnItemClickListener;

/**
 * @author liyanze
 * @create 2019/03/25
 * @Describe
 */
public class TabAdapter extends BaseRecyclerViewAdapter<String, TabAdapter.TabHolder> {

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TabHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tab, viewGroup, false);
        TabHolder holder = new TabHolder(view, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TabHolder holder, int position) {
        holder.setText(data.get(position));
    }

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

}
