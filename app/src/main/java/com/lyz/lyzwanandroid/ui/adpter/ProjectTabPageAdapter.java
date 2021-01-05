package com.lyz.lyzwanandroid.ui.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lyz.lyzwanandroid.data.model.WanAndroidData;
import com.lyz.lyzwanandroid.databinding.ItemHomeBinding;
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseRecyclerViewAdapter;
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseViewHolder;

/**
 * @author liyanze
 * @create 2019/02/18
 * @Describe
 */
public class ProjectTabPageAdapter extends BaseRecyclerViewAdapter<WanAndroidData, ProjectTabPageAdapter.ItemViewHolder> {

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHomeBinding binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(position);
    }

    public class ItemViewHolder extends BaseViewHolder {

        TextView tvChapter;
        TextView tvTitle;
        TextView tvAuthor;
        TextView tvDate;

        public ItemViewHolder(ItemHomeBinding binding) {
            super(binding);
            tvChapter = binding.tvChapter;
            tvTitle = binding.tvTitle;
            tvAuthor = binding.tvAuthor;
            tvDate = binding.tvDate;
        }

        @Override
        protected void bind(int position) {
            WanAndroidData wanAndroidData = data.get(position);
            tvChapter.setVisibility(View.GONE);

            tvTitle.setText(wanAndroidData.title);
            tvAuthor.setText(wanAndroidData.author);
            tvDate.setText(wanAndroidData.niceDate);
            itemView.setOnClickListener(this);
        }

    }
}
