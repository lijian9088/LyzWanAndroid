package com.lyz.lyzwanandroid.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * @author liyanze
 * @create 2019/04/02
 * @Describe
 */
public class BaseSwipeBackActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    /**
     * 是否开启滑动关闭
     *
     * @param isEnable
     */
    protected void enableSwipeBackLeft(boolean isEnable) {
        getSwipeBackLayout().setEnableGesture(isEnable);
    }

}
