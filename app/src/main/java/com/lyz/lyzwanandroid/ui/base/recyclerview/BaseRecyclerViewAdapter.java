package com.lyz.lyzwanandroid.ui.base.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyz.lyzwanandroid.ui.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liyanze
 * @create 2019/02/11
 * @Describe
 */
public abstract class BaseRecyclerViewAdapter<D, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> implements OnItemClickListener {

    public OnItemClickListener<D> listener;
    protected List<D> data = new ArrayList<>();

    public List<D> getData() {
        return data;
    }

    public void setData(List<D> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void appendItems(List<D> items) {
        if (items != null && items.size() > 0) {
            this.data.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void removeItem(D item) {
        data.remove(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(OnItemClickListener<D> listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.setOnItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public void onItemClick(View view, int position) {
        if (listener != null) {
            listener.onItemClick(position, data.get(position), view);
        }
    }

    public interface OnItemClickListener<D> {
        void onItemClick(int position, D data, View view);
    }
}
