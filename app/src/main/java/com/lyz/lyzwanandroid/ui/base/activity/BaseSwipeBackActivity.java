package com.lyz.lyzwanandroid.ui.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * @author liyanze
 * @create 2019/04/02
 * @Describe
 */
public class BaseSwipeBackActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    /**
     * 是否开启滑动关闭
     *
     * @param isEnable
     */
    protected void enableSwipeBackLeft(boolean isEnable) {
//        SwipeBackUtils.convertActivityToTranslucent(this);
//        getSwipeBackLayout().setEnableGesture(isEnable);
        setSwipeBackEnable(isEnable);
    }

}
