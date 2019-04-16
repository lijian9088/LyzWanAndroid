package com.lyz.lyzwanandroid.ui.adpter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.Navigation;
import com.lyz.lyzwanandroid.data.model.WanAndroidData;
import com.lyz.lyzwanandroid.ui.base.BaseRecyclerViewAdapter;
import com.lyz.lyzwanandroid.ui.module.web.WebActivity;
import com.lyz.lyzwanandroid.widget.TagTextView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * @author liyanze
 * @create 2019/03/25
 * @Describe
 */
public class TagRvAdapter extends BaseRecyclerViewAdapter<Navigation, TagRvAdapter.TagHolder> {

    @NonNull
    @Override
    public TagHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int vierType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tag, viewGroup, false);
        TagHolder holder = new TagHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {

        List<Navigation> data = getData();
        Navigation navigation = data.get(position);

        if (!TextUtils.isEmpty(navigation.name)) {
            holder.tvTitle.setText(navigation.name);
        }

        holder.flowLayout.setAdapter(new TagAdapter<WanAndroidData>(navigation.articles) {

            @Override
            public View getView(FlowLayout parent, int position, WanAndroidData wanAndroidData) {
//                TextView textView = new TextView(parent.getContext());
//                textView.setText(wanAndroidData.title);
                TagTextView textView = TagTextView.newInstance(parent.getContext());
                textView.setText(wanAndroidData.title);
                return textView;
            }
        });

        holder.flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                WanAndroidData wanAndroidData = navigation.articles.get(position);
                String link = wanAndroidData.link;
                WebActivity.goActivity(view.getContext(), link);

                return true;
            }
        });

    }

    public class TagHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TagFlowLayout flowLayout;

        public TagHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            flowLayout = itemView.findViewById(R.id.flowLayout);
        }

    }
}
