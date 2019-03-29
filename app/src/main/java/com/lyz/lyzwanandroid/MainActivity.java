package com.lyz.lyzwanandroid;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.lyz.lyzwanandroid.ui.base.BaseAppCompatActivity;
import com.lyz.lyzwanandroid.ui.base.mvp.BasePresenter;
import com.lyz.lyzwanandroid.ui.module.home.HomeFragment;
import com.lyz.lyzwanandroid.ui.module.navigation.NavigationFragment;
import com.lyz.lyzwanandroid.ui.module.project.ProjectFragment;
import com.lyz.lyzwanandroid.widget.LyzBottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseAppCompatActivity {

    @BindView(R.id.navigation)
    LyzBottomNavigationView navView;

    List<Fragment> fragmentList = new ArrayList<>();
    private FragmentManager fragmentManager;

    @Override
    protected void initView() {
        fragmentManager = getSupportFragmentManager();

        createFragments();
        setupNav();

        selectFragment(0);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void createFragments() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        HomeFragment homeFragment = new HomeFragment();
        fragmentList.add(homeFragment);
        transaction.add(R.id.container, homeFragment);

        ProjectFragment projectFragment = new ProjectFragment();
        fragmentList.add(projectFragment);
        transaction.add(R.id.container, projectFragment);

        NavigationFragment navigationFragment = new NavigationFragment();
        fragmentList.add(navigationFragment);
        transaction.add(R.id.container, navigationFragment);

        transaction.commit();
    }

    private void setupNav() {
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navHome:
                        selectFragment(0);
                        return true;
                    case R.id.navProjects:
                        selectFragment(1);
                        return true;
                    case R.id.navTree:
                        selectFragment(2);
                        return true;
                    case R.id.navNavigation:
                        selectFragment(3);
                        return true;
                    case R.id.navUser:
                        selectFragment(4);
                        return true;
                    default:
                        selectFragment(0);
                        return true;
                }
            }
        });
    }

    private void selectFragment(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment fragment = fragmentList.get(i);
            if (i == index) {
                transaction.show(fragment);
            } else {
                transaction.hide(fragment);
            }
        }

        transaction.commit();
    }
}
