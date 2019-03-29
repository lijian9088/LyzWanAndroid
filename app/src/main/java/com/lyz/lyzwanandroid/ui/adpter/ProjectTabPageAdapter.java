package com.lyz.lyzwanandroid.ui.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.WanAndroidData;
import com.lyz.lyzwanandroid.ui.base.BaseRecyclerViewWithHeaderAndFooterAdapter;
import com.lyz.lyzwanandroid.ui.base.BaseViewHolder;

import butterknife.BindView;

/**
 * @author liyanze
 * @create 2019/02/18
 * @Describe
 */
public class ProjectTabPageAdapter extends BaseRecyclerViewWithHeaderAndFooterAdapter<WanAndroidData> {

    @Override
    protected boolean hasHeader() {
        return false;
    }

    @Override
    protected BaseViewHolder createItemViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home, viewGroup, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    protected boolean hasFooter() {
        return true;
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
