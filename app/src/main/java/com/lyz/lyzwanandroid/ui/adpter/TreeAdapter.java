package com.lyz.lyzwanandroid.ui.adpter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.TreeData;
import com.lyz.lyzwanandroid.ui.base.BaseRecyclerViewAdapter;
import com.lyz.lyzwanandroid.ui.base.BaseViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;

/**
 * @author liyanze
 * @create 2019/04/19
 * @Describe
 */
public class TreeAdapter extends BaseRecyclerViewAdapter<TreeData, TreeAdapter.TreeViewHolder> {

    @NonNull
    @Override
    public TreeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tree, parent, false);
        TreeViewHolder treeViewHolder = new TreeViewHolder(view);
        return treeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TreeViewHolder holder, int position) {
        TreeData treeData = data.get(position);
        List<TreeData> children = treeData.children;

        holder.tvTitle.setText(treeData.name);
        holder.flowLayout.setAdapter(new TagAdapter<TreeData>(children) {
            @Override
            public View getView(FlowLayout parent, int position, TreeData treeData) {
                TextView textView = new TextView(parent.getContext());
                textView.setTextSize(16);
                textView.setTextColor(parent.getContext().getResources().getColor(R.color.gray));
                textView.setText(treeData.name);
                return textView;
            }
        });
    }

    class TreeViewHolder extends BaseViewHolder {

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.flowLayout)
        TagFlowLayout flowLayout;

        public TreeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
