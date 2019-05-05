package com.lyz.lyzwanandroid.ui.module.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    int currentIndex = 0;
    private SupportFragment[] mFragments = new SupportFragment[5];
    private ActionBar supportActionBar;

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
        setupToolbar();
        setupNav();
    }

    private void setupToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        supportActionBar = activity.getSupportActionBar();
        setToolbarTitle("Home");
    }

    private void setupNav() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int index;
                switch (menuItem.getItemId()) {
                    case R.id.navHome:
                        index = 0;
                        break;
                    case R.id.navProjects:
                        index = 1;
                        break;
                    case R.id.navTree:
                        index = 2;
//                        return true;
                        break;
                    case R.id.navNavigation:
                        index = 3;
                        break;
                    case R.id.navUser:
                        index = 4;
                        break;
                    default:
                        index = 0;
                        break;
                }
                selectFragment(index);
                currentIndex = index;
                setToolbarTitle(menuItem.getTitle().toString());
                return true;
            }
        });

        NavigationUtils.disableShiftMode(bottomNavigationView);
    }

    private void setToolbarTitle(String title) {
        supportActionBar.setTitle(title);
    }

    private void selectFragment(int index) {
        ISupportFragment hideFragment = mFragments[currentIndex];
        ISupportFragment showFragment = mFragments[index];
        showHideFragment(showFragment, hideFragment);
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

}
