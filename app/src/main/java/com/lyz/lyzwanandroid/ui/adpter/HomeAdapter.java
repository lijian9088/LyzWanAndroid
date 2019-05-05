package com.lyz.lyzwanandroid.ui.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lyz.lyzwanandroid.MainActivity;
import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.Banner;
import com.lyz.lyzwanandroid.data.model.WanAndroidData;
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseRecyclerViewAdapter;
import com.lyz.lyzwanandroid.ui.base.recyclerview.BaseViewHolder;
import com.lyz.lyzwanandroid.ui.module.web.WebFragment;
import com.stx.xhb.xbanner.XBanner;

import java.util.List;

import butterknife.BindView;

/**
 * @author liyanze
 * @create 2019/02/11
 * @Describe
 */
public class HomeAdapter extends BaseRecyclerViewAdapter<WanAndroidData, BaseViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<Banner> bannerData;

    public void setBannerData(List<Banner> data) {
        this.bannerData = data;
        notifyItemChanged(0);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        BaseViewHolder viewHolder;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                viewHolder = new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_header, viewGroup, false));
                break;
            case VIEW_TYPE_ITEM:
                viewHolder = new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home, viewGroup, false));
                break;
            default:
                viewHolder = new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home, viewGroup, false));
                break;
        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);
        if (viewHolder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) viewHolder).bind();
        } else {
            ((ItemViewHolder) viewHolder).bind(position);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    public class HeaderViewHolder extends BaseViewHolder {

        private final Context context;

        @BindView(R.id.xBanner)
        XBanner xBanner;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            this.context = itemView.getContext();
        }

        @Override
        protected void bind() {
            xBanner.setBannerData(bannerData);
            xBanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    //1、此处使用的Glide加载图片，可自行替换自己项目中的图片加载框架
                    //2、返回的图片路径为Object类型，你只需要强转成你传输的类型就行，切记不要胡乱强转！
                    Banner bannerModel = (Banner) model;
                    Glide.with(context)
                            .applyDefaultRequestOptions(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                            .load(bannerModel.getXBannerUrl())
                            .into((ImageView) view);
                }
            });
            xBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                @Override
                public void onItemClick(XBanner banner, Object model, View view, int position) {
                    Banner bannerModel = (Banner) model;
                    String url = bannerModel.url;
                    MainActivity act = (MainActivity) xBanner.getContext();
                    act.startChildFragment(WebFragment.newInstance(url));
                }
            });
        }
    }

    public class ItemViewHolder extends BaseViewHolder implements View.OnClickListener, com.lyz.lyzwanandroid.ui.listener.OnItemClickListener {

        @BindView(R.id.tvChapter)
        TextView tvChapter;

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvAuthor)
        TextView tvAuthor;

        @BindView(R.id.tvDate)
        TextView tvDate;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            setOnItemClickListener(this);
        }

        @Override
        protected void bind(int position) {
            WanAndroidData wanAndroidData = data.get(position - 1);
            tvChapter.setText(wanAndroidData.chapterName);
            tvTitle.setText(wanAndroidData.title);
            tvAuthor.setText(wanAndroidData.author);
            tvDate.setText(wanAndroidData.niceDate);
        }


        @Override
        public void onItemClick(View view, int position) {
            if (listener != null) {
                listener.onItemClick(position, data.get(position - 1), view);
            }
        }
    }

}
