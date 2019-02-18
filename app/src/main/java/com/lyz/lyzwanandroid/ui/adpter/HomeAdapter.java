package com.lyz.lyzwanandroid.ui.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.data.model.Article;
import com.lyz.lyzwanandroid.data.model.Banner;
import com.lyz.lyzwanandroid.ui.base.BaseRecyclerViewAdapter;
import com.lyz.lyzwanandroid.ui.base.BaseViewHolder;
import com.stx.xhb.xbanner.XBanner;

import java.util.List;

import butterknife.BindView;

/**
 * @author liyanze
 * @create 2019/02/11
 * @Describe
 */
public class HomeAdapter extends BaseRecyclerViewAdapter<Article, RecyclerView.ViewHolder> {

    public static final int STATE_FOOTER_LOADING = 0;
    public static final int STATE_FOOTER_HIDELOADING = 1;
    public static final int STATE_FOOTER_NOMORE = 2;

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_FOOTER = 2;

    private List<Banner> bannerData;
    private FooterViewHolder footerHolder;
    private OnItemClickListener itemClickListener;

    public void setBannerData(List<Banner> data) {
        this.bannerData = data;
        notifyDataSetChanged();
    }

    public void setFooterLoading() {
        footerHolder.footerContainer.setVisibility(View.VISIBLE);
        footerHolder.progressBar.setVisibility(View.VISIBLE);
        footerHolder.tvNoMore.setVisibility(View.GONE);
    }

    public void setFooterNoMore() {
        footerHolder.footerContainer.setVisibility(View.VISIBLE);
        footerHolder.progressBar.setVisibility(View.GONE);
        footerHolder.tvNoMore.setVisibility(View.VISIBLE);
    }

    public void setFooterHideLoading() {
        footerHolder.footerContainer.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                viewHolder = new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_header, viewGroup, false));
                break;
            case VIEW_TYPE_ITEM:
                viewHolder = new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home, viewGroup, false));
                break;
            case VIEW_TYPE_FOOTER:
                viewHolder = new FooterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_footer, viewGroup, false));
                footerHolder = (FooterViewHolder) viewHolder;
                break;
            default:
                viewHolder = new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home, viewGroup, false));
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) viewHolder).bind();
        } else if (viewHolder instanceof ItemViewHolder) {
            ((ItemViewHolder) viewHolder).bind(position);
        } else if (viewHolder instanceof FooterViewHolder) {

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else if (position == getItemCount() - 1) {
            return VIEW_TYPE_FOOTER;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 2;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Article article);
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
                    ToastUtils.showShort("点击了%d位置的图片", position);
                }
            });
        }
    }

    public class ItemViewHolder extends BaseViewHolder implements View.OnClickListener {

        @BindView(R.id.container)
        ViewGroup container;

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
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                int position = getLayoutPosition() - 1;
                Article article = data.get(position);
                itemClickListener.onItemClick(position, article);
            }
        }

        @Override
        protected void bind(int position) {
            container.setOnClickListener(this);

            Article article = data.get(position - 1);
            tvChapter.setText(article.chapterName);
            tvTitle.setText(article.title);
            tvAuthor.setText(article.author);
            tvDate.setText(article.niceDate);
        }


    }

    public class FooterViewHolder extends BaseViewHolder {

        @BindView(R.id.footer_layout)
        RelativeLayout footerContainer;

        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        @BindView(R.id.tvNoMore)
        TextView tvNoMore;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
