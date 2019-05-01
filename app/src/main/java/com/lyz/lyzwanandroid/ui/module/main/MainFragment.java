package com.lyz.lyzwanandroid.ui.module.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;

import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.ui.base.fragment.BaseMvpFragment;
import com.lyz.lyzwanandroid.ui.module.home.HomeFragment;
import com.lyz.lyzwanandroid.ui.module.navigation.NavigationFragment;
import com.lyz.lyzwanandroid.ui.module.project.ProjectFragment;
import com.lyz.lyzwanandroid.ui.module.tree.TreeFragment;
import com.lyz.lyzwanandroid.ui.module.user.UserFragment;
import com.lyz.lyzwanandroid.utils.NavigationUtils;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author liyanze
 * @create 2019/04/28
 * @Describe
 */
public class MainFragment extends BaseMvpFragment<MainPresenter> {

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    int currentIndex = 0;
    private SupportFragment[] mFragments = new SupportFragment[5];

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initView(View view) {
        setupNav();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        SupportFragment homeFragment = findChildFragment(HomeFragment.class);
        if (homeFragment == null) {
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = ProjectFragment.newInstance();
            mFragments[2] = TreeFragment.newInstance();
            mFragments[3] = NavigationFragment.newInstance();
            mFragments[4] = UserFragment.newInstance();

            loadMultipleRootFragment(R.id.container,
                    0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3],
                    mFragments[4]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[0] = homeFragment;
            mFragments[1] = findChildFragment(ProjectFragment.class);
            mFragments[2] = findChildFragment(TreeFragment.class);
            mFragments[3] = findChildFragment(NavigationFragment.class);
            mFragments[4] = findChildFragment(UserFragment.class);
        }
    }

    private void setupNav() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navHome:
                        selectFragment(0);
                        currentIndex = 0;
                        return true;
                    case R.id.navProjects:
                        selectFragment(1);
                        currentIndex = 1;
                        return true;
                    case R.id.navTree:
                        selectFragment(2);
                        currentIndex = 2;
                        return true;
                    case R.id.navNavigation:
                        selectFragment(3);
                        currentIndex = 3;
                        return true;
                    case R.id.navUser:
                        selectFragment(4);
                        currentIndex = 4;
                        return true;
                    default:
                        selectFragment(0);
                        return true;
                }
            }
        });

        NavigationUtils.disableShiftMode(bottomNavigationView);
    }

    private void selectFragment(int index) {
        ISupportFragment hideFragment = mFragments[currentIndex];
        ISupportFragment showFragment = mFragments[index];
        showHideFragment(showFragment, hideFragment);
    }

}
