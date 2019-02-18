package com.lyz.lyzwanandroid.ui.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liyanze
 * @create 2019/02/11
 * @Describe
 */
public abstract class BaseRecyclerViewAdapter<D, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<D> data = new ArrayList<>();

    public List<D> getData() {
        return data;
    }

    public void setData(List<D> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
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

}
