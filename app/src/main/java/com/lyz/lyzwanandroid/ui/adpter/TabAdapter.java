package com.lyz.lyzwanandroid.ui.adpter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.Navigation;
import com.lyz.lyzwanandroid.databinding.ItemTabBinding;
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
    public TabHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTabBinding binding = ItemTabBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TabHolder(binding);
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

    public static class TabHolder extends BaseViewHolder {

        private final TextView tv;

        public TabHolder(@NonNull ItemTabBinding binding) {
            super(binding);
            tv = binding.tv;
        }

        public void setText(String text) {
            tv.setText(text);
        }
    }

}
