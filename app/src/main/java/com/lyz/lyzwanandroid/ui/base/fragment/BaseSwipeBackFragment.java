package com.lyz.lyzwanandroid.ui.base.fragment;

import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * @author liyanze
 * @create 2019/04/26
 * @Describe
 */
public class BaseSwipeBackFragment extends SwipeBackFragment {
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
