package com.lyz.lyzwanandroid.ui.adpter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexboxLayout;
import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.NavLinkBean;
import com.lyz.lyzwanandroid.ui.holder.TagHolder;
import com.lyz.lyzwanandroid.ui.listener.OnTagClickListener;
import com.lyz.lyzwanandroid.widget.TagTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liyanze
 * @create 2019/03/25
 * @Describe
 */
public class TagAdapter extends RecyclerView.Adapter<TagHolder> {

    public OnTagClickListener listener;
    private List<String> titleList;
    private HashMap<String, ArrayList<NavLinkBean>> map;

    public void setData(List<String> titleList, HashMap<String, ArrayList<NavLinkBean>> map) {
        this.titleList = titleList;
        this.map = map;
    }

    @NonNull
    @Override
    public TagHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int vierType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tag, viewGroup, false);
        TagHolder holder = new TagHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {
        String title = titleList.get(position);
        holder.tvTitle.setText(title);

        ArrayList<TagTextView> tags = new ArrayList<>();
        Set<Map.Entry<String, ArrayList<NavLinkBean>>> entries = map.entrySet();

        for (Map.Entry<String, ArrayList<NavLinkBean>> next : entries) {
            String key = next.getKey();
            if (key.equals(title)) {
                ArrayList<NavLinkBean> value = next.getValue();
                for (int i = 0; i < value.size(); i++) {

                    NavLinkBean navLinkBean = value.get(i);
                    navLinkBean.position = i;

                    TagTextView tag = TagTextView.newTag(holder.tvTitle.getContext(), navLinkBean);
                    tag.setText(value.get(i).title);
                    tags.add(tag);

                }
            }
        }

        for (int i = 0; i < tags.size(); i++) {
            TagTextView tag = tags.get(i);
            holder.flexBox.addView(tag);
            FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) tag.getLayoutParams();
            layoutParams.leftMargin = 12;
            layoutParams.topMargin = 6;
            layoutParams.rightMargin = 12;
            layoutParams.bottomMargin = 6;
            tag.setLayoutParams(layoutParams);

            tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick((TagTextView) v);
                    }
                }
            });
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return titleList != null ? titleList.size() : 0;
    }

    public void setOnTagClickListen(OnTagClickListener listener) {
        this.listener = listener;
    }
}
