package com.lyz.lyzwanandroid.ui.adpter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.Navigation;
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseRecyclerViewAdapter;
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseViewHolder;

/**
 * @author liyanze
 * @create 2019/03/25
 * @Describe
 */
public class TabAdapter extends BaseRecyclerViewAdapter<Navigation, TabAdapter.TabHolder> {

    @NonNull
    @Override
    public TabHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tab, viewGroup, false);
        return new TabHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Navigation navigation = data.get(position);
        String name = navigation.name;
        if (!TextUtils.isEmpty(name)) {
            holder.setText(name);
        }
    }

    public class TabHolder extends BaseViewHolder {

        private final TextView tv;

        public TabHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }

        public void setText(String text) {
            tv.setText(text);
        }
    }

}
