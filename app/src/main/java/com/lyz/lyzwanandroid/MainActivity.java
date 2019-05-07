package com.lyz.lyzwanandroid;

import com.lyz.lyzwanandroid.ui.base.activity.BaseMvpActivity;
import com.lyz.lyzwanandroid.ui.base.mvp.BasePresenter;
import com.lyz.lyzwanandroid.ui.module.main.MainFragment;
import com.orhanobut.logger.Logger;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author liyanze
 */
public class MainActivity extends BaseMvpActivity {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        getWindow().setBackgroundDrawable(null);
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.container, MainFragment.newInstance());
        }
    }

//    @Override
//    public void setTheme(int resid) {
//        super.setTheme(R.style.NormalTheme);
//    }

    @Override
    protected void initData() {
        String apiHost = BuildConfig.BASE_URL;
        Logger.i("api_host:%s", apiHost);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    public void startChildFragment(ISupportFragment toFragment) {
        findFragment(MainFragment.class).start(toFragment);
    }

}
