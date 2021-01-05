package com.lyz.lyzwanandroid.ui.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.WanAndroidData;
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseRecyclerViewAdapter;
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseViewHolder;

import butterknife.BindView;

/**
 * @author liyanze
 * @create 2019/02/18
 * @Describe
 */
public class ProjectTabPageAdapter extends BaseRecyclerViewAdapter<WanAndroidData, ProjectTabPageAdapter.ItemViewHolder> {

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(position);
    }

    public class ItemViewHolder extends BaseViewHolder {

        @BindView(R.id.tvChapter)
        TextView tvChapter;

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvAuthor)
        TextView tvAuthor;

        @BindView(R.id.tvDate)
        TextView tvDate;

        public ItemViewHolder(View itemView) {
            super(itemView);
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
