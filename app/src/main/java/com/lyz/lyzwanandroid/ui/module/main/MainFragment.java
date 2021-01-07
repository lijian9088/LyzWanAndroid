package com.lyz.lyzwanandroid.ui.module.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lyz.lyzwanandroid.R;
import com.lyz.lyzwanandroid.databinding.FragmentMainBinding;
import com.lyz.lyzwanandroid.ui.base.fragment.BaseMvpFragment;
import com.lyz.lyzwanandroid.ui.module.home.HomeFragment;
import com.lyz.lyzwanandroid.ui.module.navigation.NavigationFragment;
import com.lyz.lyzwanandroid.ui.module.project.ProjectFragment;
import com.lyz.lyzwanandroid.ui.module.tree.TreeFragment;
import com.lyz.lyzwanandroid.ui.module.user.UserFragment;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author liyanze
 * @create 2019/04/28
 * @Describe
 */
public class MainFragment extends BaseMvpFragment<MainPresenter, FragmentMainBinding> {

    BottomNavigationView bottomNavigationView;
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
    protected FragmentMainBinding createViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMainBinding.inflate(inflater, container, false);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initView() {
        bottomNavigationView = viewBinding.navigation;
//        bottomNavigationView.setItemIconTintList(null);
        toolbar = viewBinding.toolbar;
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
                    case R.id.navProjects:
                        index = 1;
                        break;
                    case R.id.navTree:
                        index = 2;
                        break;
                    case R.id.navNavigation:
                        index = 3;
                        break;
                    case R.id.navUser:
                        index = 4;
                        break;
                    case R.id.navHome:
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
