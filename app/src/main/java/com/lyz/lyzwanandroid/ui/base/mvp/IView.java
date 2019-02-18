package com.lyz.lyzwanandroid.ui.base.mvp;

/**
 * @author liyanze
 * @create 2019/02/15
 * @Describe
 */
public interface IView {
    void showToast(String msg);

    void showToast(final String format, final Object... args);
}
