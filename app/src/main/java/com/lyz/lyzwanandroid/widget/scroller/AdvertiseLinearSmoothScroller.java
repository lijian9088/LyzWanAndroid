package com.lyz.lyzwanandroid.widget.scroller;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearSmoothScroller;

/**
 * @author liyanze
 * @create 2019/03/27
 * @Describe
 */
public class AdvertiseLinearSmoothScroller extends LinearSmoothScroller {
    public AdvertiseLinearSmoothScroller(Context context) {
        super(context);
    }

    /**
     * 此方法返回滚动每1px需要的时间,可以用来控制滚动速度
     * 即如果返回2ms，则每滚动1000px，需要2秒钟
     *
     * @param displayMetrics
     * @return
     */
    @Override
    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return 0.05f;
    }

    /**
     * @param viewStart      RecyclerView的top位置
     * @param viewEnd        RecyclerView的Bottom位置
     * @param boxStart       item的top位置
     * @param boxEnd         item的bottom位置
     * @param snapPreference 滑动方向的识别
     * @return
     */
    @Override
    public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
        //返回的就是我们item置顶需要的偏移量
        return boxStart - viewStart;
    }
}
