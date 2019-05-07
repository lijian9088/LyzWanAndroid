package com.lyz.lyzwanandroid.ui.adpter;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyz.lyzwanandroid.MainActivity;
import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.Navigation;
import com.lyz.lyzwanandroid.data.model.WanAndroidData;
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseRecyclerViewAdapter;
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseViewHolder;
import com.lyz.lyzwanandroid.ui.module.web.WebFragment;
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
//                TagTextView textView = TagTextView.newInstance(parent.getContext());
                TextView textView = new TextView(parent.getContext());
                textView.setText(wanAndroidData.title);
                Resources resources = textView.getContext().getResources();
//                textView.setTextColor(resources.getColor(R.color.primary_text));

//                textView.setBackgroundResource(R.drawable.tag_selector);
                textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                textView.setTextColor(resources.getColor(R.color.primary_text));
                textView.setPadding(10, 10, 10, 10);

                return textView;
            }
        });

        holder.flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                WanAndroidData wanAndroidData = navigation.articles.get(position);
                String link = wanAndroidData.link;

                MainActivity act = (MainActivity) holder.flowLayout.getContext();
                act.startChildFragment(WebFragment.newInstance(link));

                return true;
            }
        });

        //使用flexboxLayout，每次需要先清除view，removeAllViews()
//        List<WanAndroidData> articles = navigation.articles;
//        FlexboxLayout flexboxLayout = holder.flexboxLayout;
//        flexboxLayout.removeAllViews();
//        for (int i = 0; i < articles.size(); i++) {
//            WanAndroidData wanAndroidData = articles.get(i);
//            TagTextView textView = TagTextView.newInstance(flexboxLayout.getContext());
////            TextView textView = new TextView(holder.flexboxLayout.getContext());
//            textView.setText(wanAndroidData.title);
//            Resources resources = textView.getContext().getResources();
//            textView.setTextColor(resources.getColor(R.color.primary_text));
//            flexboxLayout.addView(textView);
//        }

    }

    public class TagHolder extends BaseViewHolder {

        public TextView tvTitle;
        public TagFlowLayout flowLayout;
//        public FlexboxLayout flexboxLayout;

        public TagHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            flowLayout = itemView.findViewById(R.id.flowLayout);
//            flexboxLayout = itemView.findViewById(R.id.flexBoxLayout);
        }

    }
}
