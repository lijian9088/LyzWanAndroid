package com.lyz.lyzwanandroid.ui.listener;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.orhanobut.logger.Logger;

/**
 * @author liyanze
 * @create 2019/02/18
 * @Describe
 */
public abstract class LyzRvListener extends RecyclerView.OnScrollListener {


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
            int realLastPosition = getRealLastPosition(recyclerView);

            String msg = "realLastPosition:%d";
            Logger.d(msg, realLastPosition);

            if (realLastPosition >= recyclerView.getLayoutManager().getItemCount() - 1) {
                onEnd();
            }

//            int lastPosition = getLastVisableItemPosition();
        }
    }

    private int getRealLastPosition(RecyclerView recyclerView) {
        int lastPosition = -1;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            //通过LayoutManager找到当前显示的最后的item的position
            lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
            //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
            lastPosition = findMax(lastPositions);
        }
        return lastPosition;
    }

    /**
     * 找到数组中的最大值
     *
     * @param lastPositions
     * @return
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    private int getLastVisableItemPosition() {
        return -1;
    }

    public abstract void onEnd();
}
