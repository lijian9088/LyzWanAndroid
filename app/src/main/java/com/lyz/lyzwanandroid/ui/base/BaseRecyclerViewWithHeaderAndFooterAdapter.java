package com.lyz.lyzwanandroid.ui.base;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyz.lyzwanandroid.R;

import butterknife.BindView;

/**
 * @author liyanze
 * @create 2019/02/18
 * @Describe
 */
public abstract class BaseRecyclerViewWithHeaderAndFooterAdapter<D> extends BaseRecyclerViewAdapter<D, BaseViewHolder> implements BaseViewHolder.OnItemClickListener {

    public static final int STATE_FOOTER_LOADING = 0;
    public static final int STATE_FOOTER_HIDELOADING = 1;
    public static final int STATE_FOOTER_NOMORE = 2;

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_FOOTER = 2;

    private FooterViewHolder footerHolder;
    private OnItemClickListener<D> itemClickListener;

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                if (hasHeader()) {
                    viewHolder = new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_header, viewGroup, false));
                }
                break;
            case VIEW_TYPE_ITEM:
                viewHolder = createItemViewHolder(viewGroup, viewType);
                viewHolder.setOnItemClickListener(this);
                break;
            case VIEW_TYPE_FOOTER:
                if (hasFooter()) {
                    viewHolder = new FooterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_footer, viewGroup, false));
                    footerHolder = (FooterViewHolder) viewHolder;
                }
                break;
            default:
                viewHolder = new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home, viewGroup, false));
                break;
        }

        return viewHolder;
    }

    protected abstract boolean hasHeader();

    protected abstract BaseViewHolder createItemViewHolder(ViewGroup viewGroup, int viewType);

    protected abstract boolean hasFooter();

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_HEADER) {
            holder.bind();
        } else if (holder.getItemViewType() == VIEW_TYPE_ITEM) {
            holder.bind(position);
        } else if (holder.getItemViewType() == VIEW_TYPE_FOOTER) {
            holder.bind();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && hasHeader()) {
            return VIEW_TYPE_HEADER;
        } else if (position == getItemCount() - 1 && hasFooter()) {
            return VIEW_TYPE_FOOTER;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = super.getItemCount();
        if (hasHeader()) {
            itemCount++;
        }
        if (hasFooter()) {
            itemCount++;
        }
        return itemCount;
    }

    public void setFooterState(int footerState) {
        switch (footerState) {
            case STATE_FOOTER_LOADING:
                setFooterLoading();
                break;
            case STATE_FOOTER_NOMORE:
                setFooterNoMore();
                break;
            case STATE_FOOTER_HIDELOADING:
            default:
                setFooterHideLoading();
        }
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

    protected void bindHeaderViewHolder(HeaderViewHolder holder) {

    }

    public void setOnItemClickListener(OnItemClickListener<D> listener) {
        this.itemClickListener = listener;
    }

    @Override
    public void onItemClick(int position) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(position, data.get(position));
        }
    }

    public interface OnItemClickListener<D> {
        void onItemClick(int position, D data);
    }

    public class HeaderViewHolder extends BaseViewHolder {

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void bind() {
            bindHeaderViewHolder(this);
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
