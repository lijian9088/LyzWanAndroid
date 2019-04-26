package com.lyz.lyzwanandroid;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.lyz.lyzwanandroid.ui.base.activity.BaseMvpActivity;
import com.lyz.lyzwanandroid.ui.base.mvp.BasePresenter;
import com.lyz.lyzwanandroid.ui.module.home.HomeMvpFragment;
import com.lyz.lyzwanandroid.ui.module.navigation.NavigationMvpFragment;
import com.lyz.lyzwanandroid.ui.module.project.ProjectMvpFragment;
import com.lyz.lyzwanandroid.ui.module.tree.TreeMvpFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author liyanze
 */
public class MainActivity extends BaseMvpActivity {

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    List<ISupportFragment> fragmentList = new ArrayList<>();
    int currentIndex = 0;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setupNav();
//        enableSwipeBackLeft(false);

        fragmentList.add(HomeMvpFragment.newInstance());
        fragmentList.add(ProjectMvpFragment.newInstance());
        fragmentList.add(TreeMvpFragment.newInstance());
        fragmentList.add(NavigationMvpFragment.newInstance());

        if (findFragment(HomeMvpFragment.class) == null) {
            loadMultipleRootFragment(R.id.container,
                    0,
                    fragmentList.get(0),
                    fragmentList.get(1),
                    fragmentList.get(2),
                    fragmentList.get(3));
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
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

        disableShiftMode(bottomNavigationView);
    }

    private void selectFragment(int index) {
//        if (index == currentIndex) {
//            //重复点击
//
//        } else {
            ISupportFragment hideFragment = fragmentList.get(currentIndex);
            ISupportFragment showFragment = fragmentList.get(index);
            showHideFragment(showFragment, hideFragment);
//        }
    }

    public void disableShiftMode(BottomNavigationView navigationView) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
        try {
            // 利用反射，改变 item 中 mShiftingMode 的值 ,从而改变 BottomNavigationView 默认的效果
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
