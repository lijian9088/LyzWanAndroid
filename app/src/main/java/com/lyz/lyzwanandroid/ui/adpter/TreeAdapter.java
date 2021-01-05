package com.lyz.lyzwanandroid.ui.adpter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ConvertUtils;
import com.google.android.flexbox.FlexboxLayout;
import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.TreeData;
import com.lyz.lyzwanandroid.databinding.ItemTreeBinding;
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseRecyclerViewAdapter;
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseViewHolder;

import java.util.List;

/**
 * @author liyanze
 * @create 2019/04/19
 * @Describe
 */
public class TreeAdapter extends BaseRecyclerViewAdapter<TreeData, TreeAdapter.TreeViewHolder> {

    @NonNull
    @Override
    public TreeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTreeBinding binding = ItemTreeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TreeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TreeViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        TreeData treeData = data.get(position);
        List<TreeData> children = treeData.children;

        holder.tvTitle.setText(treeData.name);

        holder.flexboxLayout.removeAllViews();
        for (int i = 0; i < children.size(); i++) {
            TreeData data = children.get(i);
            TextView textView = new TextView(holder.flexboxLayout.getContext());
            textView.setTextSize(16);
            textView.setTextColor(holder.flexboxLayout.getContext().getResources().getColor(R.color.gray));
            textView.setText(data.name);
            holder.flexboxLayout.addView(textView);

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
            int margin = ConvertUtils.dp2px(5);
            layoutParams.setMargins(margin, margin, margin, margin);
            textView.setLayoutParams(layoutParams);
        }

    }

    static class TreeViewHolder extends BaseViewHolder {

        public TextView tvTitle;
        public FlexboxLayout flexboxLayout;

        public TreeViewHolder(@NonNull ItemTreeBinding binding) {
            super(binding);
            tvTitle = binding.tvTitle;
            flexboxLayout = binding.flexBoxLayout;
        }
    }
}
